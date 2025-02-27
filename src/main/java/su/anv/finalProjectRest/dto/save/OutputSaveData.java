package su.anv.finalProjectRest.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Информация по тикеру по отдельным датам")
public class OutputSaveData {
    private LocalDate date;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;

}
