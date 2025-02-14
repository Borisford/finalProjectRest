package su.anv.finalProjectRest.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedResult {

     private boolean adjusted;
     @JsonProperty("next_url")
     private  String url;
     private Integer queryCount;
     @JsonProperty("request_id")
     private String requestId;
     List<TradesData> results;
     @JsonProperty("results_count")
     private Integer resultsCount;
     private String status;
     @JsonProperty("ticker")
     private String tickerName;
}
