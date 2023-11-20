package com.crusader.bt.repos.custom.impl;

import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.mappers.UserMapper;
import com.crusader.bt.repos.UserRoleRepository;
import com.crusader.bt.repos.custom.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final UserRoleRepository userRoleRepository;
    private final DatabaseClient client;
    private final UserMapper mapper;

    private static final String SELECT_QUERY = """
                    select u.id as user_id, u.username as user_name, u.pass as user_pass, 
                                    r.id as role_id, r.role_name as role_name, r.description as role_desc 
                                    from users u 
                                    left join user_role ur on u.id = ur.usr_id 
                                    left join roles r on r.id = ur.role_id 
                                    where u.username = :username
            """;

    /**
     * Кастомный запрос для получения пользователя по его username,
     * подгружает сущности role с отношением many-to-many
     */
    @Override
    public Mono<UserEntity> findByUsername(String username) {

        return client.sql(SELECT_QUERY)
                .bind("username", username)
                .map(mapper)
                .all()
                .reduce(
                        (f, s) -> {
                            f.getRoles().addAll(s.getRoles());
                            return f;
                        }
                );
    }

    /**
     * Saves and returns a Department.
     */
    @Override
    @Transactional
    public Mono<UserEntity> save(UserEntity department) {
        return this.saveUser(department)
                .flatMap(this::saveRoles)
                .flatMap(this::saveUserRoles);
    }

    /**
     * Save new user
     */
    private Mono<UserEntity> saveUser(UserEntity user) {
        if (user.getId() == null) {
            return client.sql("insert into users(username, pass) values (:name, :pass)")
                    .bind("name", user.getUsername())
                    .bind("pass", user.getPassword())
                    .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                    .fetch().first()
                    .doOnNext(result -> user.setId(Long.parseLong(result.get("id").toString())))
                    .thenReturn(user);
        } else {
            return this.client.sql("update users set username = :name where id = :id")
                    .bind("name", user.getUsername())
                    .fetch().first()
                    .thenReturn(user);
        }
    }

    /**
     * Save user roles
     */
    private Mono<UserEntity> saveRoles(UserEntity user) {
        return Flux.fromIterable(user.getRoles())
                .flatMap(
                        userRoleEntity ->
                                userRoleRepository.findByName(userRoleEntity.getName())
                                        .defaultIfEmpty(userRoleEntity)
                )
                .flatMap(userRoleRepository::save)
                .collect(Collectors.toSet())
                .doOnNext(user::setRoles)
                .thenReturn(user);
    }

    /**
     * Save the relationship between User and Roles.
     */
    private Mono<UserEntity> saveUserRoles(UserEntity user) {
        String query = "insert into user_role(usr_id, role_id) VALUES (:id, :roleId)";

        return Flux.fromIterable(user.getRoles())
                .flatMap(role -> client.sql(query)
                        .bind("id", user.getId())
                        .bind("roleId", role.getId())
                        .fetch().rowsUpdated())
                .collectList()
                .thenReturn(user);
    }

}
