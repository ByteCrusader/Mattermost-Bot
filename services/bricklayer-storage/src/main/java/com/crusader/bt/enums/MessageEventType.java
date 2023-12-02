package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    PROCESSING_CREATE_BOT_EVENT("bot_create_processing"),
    PROCESSED_CREATE_BOT_EVENT("bot_create_processed"),
    FAIL_CREATE_BOT_EVENT("bot_create_failed"),
    PROCESSING_EDIT_BOT_EVENT("bot_update_processing"),
    PROCESSED_EDIT_BOT_EVENT("bot_update_processed"),
    FAIL_EDIT_BOT_EVENT("bot_update_failed"),
    PROCESSING_DELETE_BOT_EVENT("bot_delete_processing"),
    PROCESSED_DELETE_BOT_EVENT("bot_delete_processed"),
    FAIL_DELETE_BOT_EVENT("bot_delete_failed"),
    ;

    private final String name;
}
