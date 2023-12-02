package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    CREATE_BOT_EVENT("bot_create_launch"),
    COMPLETE_CREATE_BOT_EVENT("bot_create_success"),
    FAIL_CREATE_BOT_EVENT("bot_create_failed"),
    EDIT_BOT_EVENT("bot_update_launch"),
    COMPLETE_EDIT_BOT_EVENT("bot_update_success"),
    FAIL_EDIT_BOT_EVENT("bot_update_failed"),
    DELETE_BOT_EVENT("bot_delete_launch"),
    COMPLETE_DELETE_BOT_EVENT("bot_delete_success"),
    FAIL_DELETE_BOT_EVENT("bot_delete_failed"),
    CREATE_JOB_EVENT("job_create_launch");

    private final String name;
}
