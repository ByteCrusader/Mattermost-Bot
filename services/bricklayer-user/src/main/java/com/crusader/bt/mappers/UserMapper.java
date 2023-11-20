package com.crusader.bt.mappers;

import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.entity.UserRoleEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserMapper implements BiFunction<Row, RowMetadata, UserEntity> {

    /**
     * Маппер для конвертации данных из sql запроса в сущность пользователя
     */
    @Override
    public UserEntity apply(Row row, RowMetadata rowMetadata) {

        Set<UserRoleEntity> roles = Stream
                .of(
                        UserRoleEntity.builder()
                                .id(row.get("role_id", Long.class))
                                .name(row.get("role_name", String.class))
                                .description(row.get("role_desc", String.class))
                                .build()
                )
                .collect(Collectors.toSet());

        return UserEntity.builder()
                .id(row.get("user_id", Long.class))
                .username(row.get("user_name", String.class))
                .password(row.get("user_pass", String.class))
                .roles(roles)
                .build();
    }

}
