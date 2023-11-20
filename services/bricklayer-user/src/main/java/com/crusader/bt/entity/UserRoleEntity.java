package com.crusader.bt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    /**
     * Наименование ролей
     */
    @Column("role_name")
    private String name;

    /**
     * Описание ролей
     */
    @Column("description")
    private String description;
}
