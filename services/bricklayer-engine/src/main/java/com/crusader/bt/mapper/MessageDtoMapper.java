package com.crusader.bt.mapper;

import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageDtoMapper {

    /**
     * Map Cron dto object into message dto
     */
    MessageDto mapToMessageDto(CronDto botDto);

}
