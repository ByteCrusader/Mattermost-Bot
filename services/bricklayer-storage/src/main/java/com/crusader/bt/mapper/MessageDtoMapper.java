package com.crusader.bt.mapper;

import com.crusader.bt.dto.BotDto;
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
     * Map Bot dto object into message dto
     */
    MessageDto mapToMessageDto(BotDto botDto);

}
