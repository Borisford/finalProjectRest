package su.anv.finalProjectRest.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос на сохранение данных")
public class IncomingSave{

    @Schema(description = "Биржевай тикер", example = "AAPL")
    @Size(min = 3, max = 4, message = "Тикер должен содержать 4 символа")
    @NotBlank(message = "Тикер не может быть пустыми")
    private String ticker;

    @Schema(description = "Дата начала запроса", example = "2024-01-02")
    @Size(min = 10, max = 10, message = "Дата должена содержать 10 символа")
    @NotBlank(message = "Дата не может быть пустыой")
    private String start;


    @Schema(description = "Дата окончания запроса", example = "2024-01-03")
    @Size(min = 10, max = 10, message = "Дата должена содержать 10 символа")
    @NotBlank(message = "Дата не может быть пустыой")
    private String end;

}
