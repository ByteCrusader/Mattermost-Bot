package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    CREATE_BOT_EVENT("bot_create_launch"),
    PROCESSING_CREATE_BOT_EVENT("bot_create_processing"),
    PROCESSED_CREATE_BOT_EVENT("bot_create_processed"),
    COMPLETE_CREATE_BOT_EVENT("bot_create_success"),
    EDIT_BOT_EVENT("bot_update_launch"),
    PROCESSING_EDIT_BOT_EVENT("bot_update_processing"),
    PROCESSED_EDIT_BOT_EVENT("bot_update_processed"),
    COMPLETE_EDIT_BOT_EVENT("bot_update_success"),
    DELETE_BOT_EVENT("bot_delete_launch"),
    PROCESSING_DELETE_BOT_EVENT("bot_delete_processing"),
    PROCESSED_DELETE_BOT_EVENT("bot_delete_processed"),
    COMPLETE_DELETE_BOT_EVENT("bot_delete_success"),
    PROCESSING_CREATE_JOB_EVENT("job_create_processing"),
    PROCESSED_CREATE_JOB_EVENT("job_create_processed"),
    COMPLETE_CREATE_JOB_EVENT("job_create_success"),
    ;

    private final String name;
}
