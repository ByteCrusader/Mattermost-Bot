package com.crusader.bt.converter;

import com.crusader.bt.dto.UserDto;
import com.crusader.bt.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity source) {
        return UserDto.builder()
                .username(source.getUsername())
                .build();
    }

}
