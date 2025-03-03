package su.anv.finalProjectRest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickers")
public class Ticker {
    @Id
    @Column(name = "ticker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID  id;

    @Column(name = "ticker", unique = true, nullable = false)
    private String tickerName;

    @OneToMany
    @JoinColumn(name = "ticker_id")
    private List<Request> requests;


}