package com.concertticketing.userapi.common.mapstruct;

import static org.mapstruct.MappingConstants.ComponentModel.*;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
    componentModel = SPRING,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface MapStructBaseConfig {
}
