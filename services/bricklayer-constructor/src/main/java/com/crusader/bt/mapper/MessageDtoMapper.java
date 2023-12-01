package com.crusader.bt.mapper;

import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageDtoMapper {

    /**
     * Map Bot dto object into message dto
     */
    MessageDto mapToMessageDto(BotDto botDto);

    /**
     * Map message dto object into message dto
     */
    BotDto mapToBotDto(MessageDto botDto);

    /**
     * Map Cron dto object into message dto
     */
    @Mapping(target = "jobCron", source = "cronDto.cron")
    @Mapping(target = "jobMessage", source = "cronDto.message")
    MessageDto mapToMessageDto(CronDto cronDto);

}
