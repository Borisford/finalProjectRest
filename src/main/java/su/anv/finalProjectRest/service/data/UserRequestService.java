package su.anv.finalProjectRest.service.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.entity.*;
import su.anv.finalProjectRest.error.exception.EndBeforeStartException;
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.repository.UserRequestRepository;
import su.anv.finalProjectRest.dto.save.IncomingSave;
import su.anv.finalProjectRest.dto.save.OutputSave;
import su.anv.finalProjectRest.dto.save.OutputSaveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRequestService {
    private final RequestRepository requestRepository;
    private final UserRequestRepository userRequestRepository;
    private final TickerService tickerService;

    @Transactional
    public void addUserRequest(User user, IncomingSave incomingSave) {
        LocalDate start = LocalDate.parse(incomingSave.getStart());
        LocalDate end = LocalDate.parse(incomingSave.getEnd());
        if (!start.isBefore(end)) {
            throw new EndBeforeStartException(incomingSave.getStart(), incomingSave.getEnd());
        }
        Ticker ticker = tickerService.getByTickerName(incomingSave.getTicker());

        List<UserRequest> userRequestList = new ArrayList<>();

        Optional<List<Request>> optionalPriceFromLocalBase = requestRepository.findByTicker(ticker);
        Optional<List<UserRequest>> optionalUserRequestsFromLocalBase = userRequestRepository.findByUser(user);

        for (LocalDate now = start; !now.isAfter(end); now = now.plusDays(1)) {
            Request request = getPriceFromLocalBase(optionalPriceFromLocalBase, now);
            if (request != null && !localBaseContainsUserRequest(optionalUserRequestsFromLocalBase, request)) {
                userRequestList.add(UserRequest.builder()
                        .user(user)
                        .request(request)
                        .build());

            }
        }
        userRequestRepository.saveAll(userRequestList);
    }

    @Transactional
    public OutputSave getSavedData(User user, String tickerName) {
        Ticker ticker = tickerService.getByTickerName(tickerName);
        List<Request> requestList = requestRepository.findAllByUserIdAndTicker(user, ticker);

        List<OutputSaveData> outputSaveDataList = null;
        if (requestList != null && !requestList.isEmpty()) {
            outputSaveDataList = new ArrayList<>();
            for (Request request : requestList) {
                Base base = request.getBase();
                if (base != null) {
                    outputSaveDataList.add(outputSaveDataBuilder(request, base));
                }

            }
        }

        return OutputSave.builder().tickerName(ticker.getTickerName()).data(outputSaveDataList).build();
    }



    private boolean localBaseContainsUserRequest(Optional<List<UserRequest>> userRequestList, Request request) {
        boolean res = false;

        if (userRequestList.isPresent()) {
            for (UserRequest userRequest : userRequestList.get()) {
                if (userRequest.getRequest().equals(request)) {
                    res = true;
                    break;
                }
            }
        }

        return res;
    }

    private Request getPriceFromLocalBase(Optional<List<Request>> requestList, LocalDate date) {
        Request res = null;

        if (requestList.isPresent()) {
            for (Request request : requestList.get()) {
                if (request.getDate().equals(date)) {
                    res = request;
                    break;
                }
            }
        }

        return res;
    }

    private OutputSaveData outputSaveDataBuilder(Request request, Base base) {
        OutputSaveData outputSaveData = OutputSaveData.builder()
                .date(request.getDate())
                .close(base.getClose())
                .high(base.getHigh())
                .low(base.getLow())
                .open(base.getOpen())
                .build();
        return outputSaveData;
    }
}
