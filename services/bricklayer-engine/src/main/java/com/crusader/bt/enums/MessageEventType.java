package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    PROCESSING_CREATE_JOB_EVENT("job_create_processing"),
    PROCESSED_CREATE_JOB_EVENT("job_create_processed"),
    FAIL_CREATE_JOB_EVENT("job_create_failed"),
    ;

    private final String name;
}
