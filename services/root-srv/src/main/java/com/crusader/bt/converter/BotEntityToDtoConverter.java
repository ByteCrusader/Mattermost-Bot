package com.crusader.bt.converter;

import com.crusader.bt.dto.BotDto;
import com.crusader.bt.entity.BotEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BotEntityToDtoConverter implements Converter<BotEntity, BotDto> {

    @Override
    public BotDto convert(BotEntity source) {
        return BotDto.builder()
                .userId(source.getUserId())
                .createAt(source.getCreateAt())
                .updateAt(source.getUpdateAt())
                .deleteAt(source.getDeleteAt())
                .username(source.getUsername())
                .displayName(source.getDisplayName())
                .description(source.getDescription())
                .ownerId(source.getOwnerId())
                .build();
    }

}
