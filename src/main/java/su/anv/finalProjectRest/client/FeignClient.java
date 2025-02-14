package su.anv.finalProjectRest.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import su.anv.finalProjectRest.client.dto.SavedResult;

@org.springframework.cloud.openfeign.FeignClient(name = "${feign.name}", url = "${feign.polygon-service-url}")
public interface FeignClient {
    @GetMapping(value = "/v2/aggs/ticker/{stocksTicker}/range/1/day/{from}/{to}?adjusted=true&sort=asc&apiKey={apiKey}")
    SavedResult getInfoByPeriod(@PathVariable("stocksTicker") String tickerName,
                                @PathVariable("from") String from,
                                @PathVariable("to") String to,
                                @PathVariable("apiKey") String apiKey);

}
