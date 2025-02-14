package su.anv.finalProjectRest.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.error.exception.NoTickerInBaseException;
import su.anv.finalProjectRest.repository.TickerRepository;

@Service
@RequiredArgsConstructor
public class TickerService {
    private final TickerRepository tickerRepository;

    public Ticker getByTickerName(String tickerName) {
        return tickerRepository.findByTickerName(tickerName)
                .orElseThrow(() -> new NoTickerInBaseException(tickerName));

    }
}
