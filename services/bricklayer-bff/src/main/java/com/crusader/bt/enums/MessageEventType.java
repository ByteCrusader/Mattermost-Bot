package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    CREATE_BOT_EVENT("bot_create_launch"),
    EDIT_BOT_EVENT("bot_update_launch"),
    DELETE_BOT_EVENT("bot_delete_launch");

    private final String name;
}
