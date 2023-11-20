package com.crusader.bt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Важный момент, Spring r2jdbs использует стратегию IsNewStrategy при проверке внесения сущности в БД.
 * При save в repository идет проверка:
 * если id поле null, если объект, или 0, если примитив - происходит Insert в БД;
 * в любом другом случае - выполняется Update.
 * Если не держать этот момент в уме, можно получить следующую ошибку:
 * Failed to update table [table name]. Row with Id [value] does not exist.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {

    /**
     * Идентификатор сущности в БД
     */
    @Id
    private Long id;
}
