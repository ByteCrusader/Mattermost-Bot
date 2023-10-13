package com.crusader.bt.entity;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

/**
 * A bot account
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bots")
public class BotEntity {

    @Id
    private Long id;

    /**
     * The user id of the associated user entry
     */
    @Column("user_id")
    private String userId;
    /**
     * The time in milliseconds a bot was created
     */
    @Column("create_at")
    private BigInteger createAt;
    /**
     * The time in milliseconds a bot was last updated
     */
    @Column("update_at")
    private BigInteger updateAt;
    /**
     * The time in milliseconds a bot was deleted
     */
    @Column("delete_at")
    private BigInteger deleteAt;
    @Column("username")
    private String username;
    @Column("display_name")
    private String displayName;
    @Column("description")
    private String description;
    /**
     * The user id of the user that currently owns this bot
     */
    @Column("owner_id")
    private String ownerId;

}

