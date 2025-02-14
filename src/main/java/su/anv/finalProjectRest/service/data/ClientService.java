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
import su.anv.finalProjectRest.repository.RequestRepository;
import su.anv.finalProjectRest.schema.save.IncomingSave;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final FeignClient feignClient;
    private final TickerService tickerService;
    private final RequestRepository requestRepository;
    private final Long millisInDay = (long) (24 * 60 * 60 * 1000);

    public void saveToLocal(IncomingSave incomingSave) {
        Ticker ticker = tickerService.getByTickerName(incomingSave.getTicker());
        SavedResult sr = feignClient.getInfoByPeriod(incomingSave.getTicker(), incomingSave.getStart(), incomingSave.getEnd(), Dotenv.load().get("POLIGON_API_KEY"));

        List<TradesData> prices = sr.getResults();
        Map<Timestamp, Base> dataBase = new HashMap<>();

        Base base;
        for (TradesData     tradesData: prices) {
            base = Base.builder()
                    .open(tradesData.getOpen())
                    .close(tradesData.getClose())
                    .high(tradesData.getHigh())
                    .low(tradesData.getLow())
                    .build();
            dataBase.put(tradesData.getTimestamp(), base);
        }

        Timestamp start = Collections.min(dataBase.keySet());
        Timestamp stop = Collections.max(dataBase.keySet());
        Timestamp now = start;


        Request request;
        List<Request> requestBase = new ArrayList<>();
        while (!(now.after(stop))) {
            if (requestRepository.findByTickerAndDate(ticker, LocalDate.from(now.toLocalDateTime())).isEmpty()) {
                request = Request.builder()
                        .ticker(ticker)
                        .date(LocalDate.from(now.toLocalDateTime()))
                        .base(dataBase.getOrDefault(now, null))
                        .build();

                requestBase.add(request);
            }
            now.setTime(now.getTime() + millisInDay);
        }

        requestRepository.saveAll(requestBase);
    }
}
