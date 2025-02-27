package su.anv.finalProjectRest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import su.anv.finalProjectRest.client.dto.TradesData;
import su.anv.finalProjectRest.entity.Base;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(source = "open", target = "open")
    @Mapping(source = "close", target = "close")
    @Mapping(source = "high", target = "high")
    @Mapping(source = "low", target = "low")
    Base toEntity(TradesData tradesData);
}
