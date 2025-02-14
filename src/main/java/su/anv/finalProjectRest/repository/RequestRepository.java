package su.anv.finalProjectRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import su.anv.finalProjectRest.entity.Request;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.entity.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {
    Optional<Request> findByTickerAndDate(Ticker ticker, LocalDate date);
    @Query("SELECT r FROM Request AS r JOIN UserRequest AS ur ON ur.request = r\n" +
            "WHERE ur.user = %:user% AND r.ticker = %:ticker%")
    List<Request> findAllByUserIdAndTicker(@Param("user") User user, @Param("ticker") Ticker ticker);
    @Query("SELECT MAX(r.date) FROM Request AS r JOIN Ticker AS t ON r.ticker = t WHERE t.tickerName = %:ticker%")
    Optional<LocalDate> findMaxSavedDateByTicker(@Param("ticker") String ticker);
}
