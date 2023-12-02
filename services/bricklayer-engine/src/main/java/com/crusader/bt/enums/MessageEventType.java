package com.crusader.bt.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEventType {

    CREATE_JOB_EVENT("job_create_launch"),
    COMPLETE_JOB_EVENT("job_create_success"),
    FAIL_JOB_EVENT("job_create_failed"),
    ;

    private final String name;
}
