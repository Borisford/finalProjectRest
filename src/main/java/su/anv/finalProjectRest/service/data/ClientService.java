package su.anv.finalProjectRest.service.data;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.client.FeignClient;
import su.anv.finalProjectRest.client.dto.SavedResult;
import su.anv.finalProjectRest.client.dto.TradesData;
import su.anv.finalProjectRest.entity.Base;
import su.anv.finalProjectRest.entity.Request;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.mapper.PriceMapper;
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.dto.save.IncomingSave;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final FeignClient feignClient;
    private final TickerService tickerService;
    private final RequestRepository requestRepository;
    private final EmptyDateService emptyDateService;
    private final PriceMapper priceMapper;

    public void saveToLocal(IncomingSave incomingSave) {
        Ticker ticker = tickerService.getByTickerName(incomingSave.getTicker());
        SavedResult sr = feignClient.getInfoByPeriod(incomingSave.getTicker(), incomingSave.getStart(), incomingSave.getEnd(), Dotenv.load().get("POLIGON_API_KEY"));

        List<TradesData> prices = sr.getResults();
        Map<Timestamp, Base> dataBase = new HashMap<>();

        for (TradesData tradesData: prices) {
            dataBase.put(tradesData.getTimestamp(), priceMapper.toEntity(tradesData));
        }

        Timestamp start = Collections.min(dataBase.keySet());
        Timestamp stop = Collections.max(dataBase.keySet());

        Set<Timestamp> emptyDates = emptyDateService.emptyDates(ticker, start, stop);
        List<Request> requestBase = new ArrayList<>();
        for (Timestamp now:emptyDates) {
            Request request = Request.builder()
                    .ticker(ticker)
                    .date(LocalDate.from(now.toLocalDateTime()))
                    .base(dataBase.getOrDefault(now, null))
                    .build();

            requestBase.add(request);
        }

        requestRepository.saveAll(requestBase);
    }
}
