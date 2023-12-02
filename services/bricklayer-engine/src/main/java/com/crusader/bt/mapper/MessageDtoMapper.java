package com.crusader.bt.mapper;

import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageDtoMapper {

    /**
     * Map Cron dto object into message dto
     */
    MessageDto mapToMessageDto(CronDto botDto);

}
