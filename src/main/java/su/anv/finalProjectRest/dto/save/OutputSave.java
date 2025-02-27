package su.anv.finalProjectRest.dto.save;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Информация по тикеру за все даты")
public class OutputSave {
    @JsonProperty("ticker")
    private String tickerName;
    List<OutputSaveData> data;

}
