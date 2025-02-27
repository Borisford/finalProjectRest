package su.anv.finalProjectRest.service.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.anv.finalProjectRest.entity.Request;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.entity.User;
import su.anv.finalProjectRest.repository.RequestRepository;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmptyDateService {
    private final RequestRepository requestRepository;
    private final Long millisInDay = (long) (24 * 60 * 60 * 1000);

    public Set<Timestamp> emptyDates(Ticker ticker, Timestamp start, Timestamp end) {
        Set<Timestamp> res = new HashSet<>();
        for (Timestamp now = start; !now.after(end); now.setTime(now.getTime() + millisInDay)) {
            res.add(now);
        }
        Optional<List<Request>> requestList = requestRepository.findByTickerAndDateBetween(ticker, start, end);
        if (requestList.isPresent()) {
            for (Request request:requestList.get()) {
                res.remove(request.getDate());
            }
        }
        return res;
    }
}
