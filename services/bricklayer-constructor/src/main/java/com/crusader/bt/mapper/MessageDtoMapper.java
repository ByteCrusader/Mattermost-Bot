package com.crusader.bt.mapper;

import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageDtoMapper {

    /**
     * Map Bot dto object into message dto
     */
    MessageDto mapToMessageDto(BotDto botDto);

}
