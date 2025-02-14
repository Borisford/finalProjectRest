package su.anv.finalProjectRest.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.entity.*;
import su.anv.finalProjectRest.error.exception.EndBeforeStartException;
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.repository.UserRequestRepository;
import su.anv.finalProjectRest.schema.save.IncomingSave;
import su.anv.finalProjectRest.schema.save.OutputSave;
import su.anv.finalProjectRest.schema.save.OutputSaveData;

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

    public void addUserRequest(User user, IncomingSave incomingSave) {
        LocalDate start = LocalDate.parse(incomingSave.getStart());
        LocalDate end = LocalDate.parse(incomingSave.getEnd());
        if (!start.isBefore(end)) {
            throw new EndBeforeStartException(incomingSave.getStart(), incomingSave.getEnd());
        }
        Ticker ticker = tickerService.getByTickerName(incomingSave.getTicker());

        UserRequest userRequest;
        Optional<Request> priceFromLockalBase;
        Optional<UserRequest> optionalUserRequest;
        List<UserRequest> userRequestList = new ArrayList<>();
        LocalDate now = start;
        while (!now.isAfter(end)) {
            priceFromLockalBase = requestRepository.findByTickerAndDate(ticker, now);
            if (priceFromLockalBase.isPresent()) {
                optionalUserRequest = userRequestRepository.findByUserAndRequest(user, priceFromLockalBase.get());
                if (optionalUserRequest.isEmpty()) {
                    userRequest = UserRequest.builder()
                            .user(user)
                            .request(priceFromLockalBase.get())
                            .build();
                    userRequestList.add(userRequest);
                }
            }
            now = now.plusDays(1);
        }
        userRequestRepository.saveAll(userRequestList);
    }

    public OutputSave getSavedData(User user, String tickerName) {

        Ticker ticker = tickerService.getByTickerName(tickerName);
        OutputSave res = OutputSave.builder().tickerName(ticker.getTickerName()).build();
        List<Request> requestList = requestRepository.findAllByUserIdAndTicker(user, ticker);
        List<OutputSaveData> outputSaveDataList = null;
        OutputSaveData outputSaveData;
        if (requestList != null && !requestList.isEmpty()) {
            outputSaveDataList = new ArrayList<>();
            for (Request request: requestList){
                Base base = request.getBase();
                if (base != null) {
                    outputSaveData = OutputSaveData.builder()
                            .date(request.getDate())
                            .close(base.getClose())
                            .high(base.getHigh())
                            .low(base.getLow())
                            .open(base.getOpen())
                            .build();
                    outputSaveDataList.add(outputSaveData);
                }

            }
        }
        res.setData(outputSaveDataList);
        return res;
    }
}
