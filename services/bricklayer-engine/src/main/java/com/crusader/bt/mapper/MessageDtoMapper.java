package com.crusader.bt.mapper;

import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MessageDtoMapper {

    /**
     * Map Cron dto object into message dto
     */
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    MessageDto mapToMessageDto(CronDto botDto);

}
