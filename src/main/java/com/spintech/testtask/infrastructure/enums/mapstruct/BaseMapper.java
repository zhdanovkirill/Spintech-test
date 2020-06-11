package com.spintech.testtask.infrastructure.enums.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BaseMapper {
}
