package su.anv.finalProjectRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, UUID> {
    Optional<Ticker> findById(UUID id);
    Optional<Ticker> findByTickerName(String tickerName);
}
