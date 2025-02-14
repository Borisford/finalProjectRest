package su.anv.finalProjectRest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
public class Base {
    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "price_id")
    private Request request;

    @Column(name = "open", unique = false, nullable = false)
    private BigDecimal open;

    @Column(name = "close", unique = false, nullable = false)
    private BigDecimal close;

    @Column(name = "high", unique = false, nullable = false)
    private BigDecimal high;

    @Column(name = "low", unique = false, nullable = false)
    private BigDecimal low;

}