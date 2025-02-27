package su.anv.finalProjectRest.service.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.client.FeignClient;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.repository.TickerRepository;
import su.anv.finalProjectRest.dto.save.IncomingSave;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.google.common.util.concurrent.RateLimiter;

@Service
@RequiredArgsConstructor
public class LocalBaseInputService {
    private final FeignClient feignClient;
    private final ClientService clientService;
    private final RequestRepository requestRepository;
    private final TickerRepository tickerRepository;
    private final static double MAX_REQUEST_PER_MINUTE = 5;
    private final static double SECONDS_PER_MINUTE = 60;
    private final RateLimiter rateLimiter = RateLimiter.create(MAX_REQUEST_PER_MINUTE/SECONDS_PER_MINUTE);

    @Transactional
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
            rateLimiter.acquire();
            clientService.saveToLocal(incomingSave);
        }
    }

    public void addTicker(String tickerName) {
        Ticker ticker = Ticker.builder().tickerName(tickerName).build();
        tickerRepository.save(ticker);
    }



}
