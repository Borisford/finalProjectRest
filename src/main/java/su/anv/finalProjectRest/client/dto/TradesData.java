package su.anv.finalProjectRest.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradesData {

    @JsonProperty("c")
    private BigDecimal close;
    @JsonProperty("h")
    private BigDecimal high;
    @JsonProperty("l")
    private BigDecimal low;
    @JsonProperty("n")
    private BigDecimal number;
    @JsonProperty("o")
    private BigDecimal open;
    @JsonProperty("t")
    private Timestamp timestamp;
    @JsonProperty("v")
    private BigDecimal volume;
    @JsonProperty("vw")
    private BigDecimal averagePrice;

}