package su.anv.finalProjectRest.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.client.FeignClient;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.repository.TickerRepository;
import su.anv.finalProjectRest.schema.save.IncomingSave;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocalBaseInputService {
    private final FeignClient feignClient;
    private final ClientService clientService;
    private final RequestRepository requestRepository;
    private final TickerRepository tickerRepository;
    private static LocalDateTime time;
    private final static int MAX_REQUEST_PER_MINUTE = 5;
    private final static long MIN_SECONDS_BEFORE_NEXT_REQUEST = 60 / MAX_REQUEST_PER_MINUTE;

    static {
        time = LocalDateTime.now();
    }

    public void saveToLocal(String tickerName) {
        IncomingSave incomingSave = IncomingSave.builder().ticker(tickerName).build();
        Optional<LocalDate> date = requestRepository.findMaxSavedDateByTicker(tickerName);
        LocalDate start, end;
        if (date.isEmpty()) {
            start = LocalDate.now().minusYears(2);
        } else{
            start = date.get().plusDays(1);
        }
        end = LocalDate.now().minusDays(1);
        if (start.isBefore(end)) {
            incomingSave.setStart(start.toString());
            incomingSave.setEnd(end.toString());
            System.out.println("Start = " + incomingSave.getStart());
            System.out.println("End = " + incomingSave.getEnd());
            waitAndSaveToLocal(incomingSave);
        }
    }

    private void waitAndSaveToLocal(IncomingSave incomingSave) {
        if (!time.isBefore(LocalDateTime.now())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            waitAndSaveToLocal(incomingSave);
        } else {
            clientService.saveToLocal(incomingSave);
            time = LocalDateTime.now().plusSeconds(MIN_SECONDS_BEFORE_NEXT_REQUEST);
        }
    }

    public void addTicker(String tickerName) {
        Ticker ticker = Ticker.builder().tickerName(tickerName).build();
        tickerRepository.save(ticker);
    }



}
