package com.web.vt.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum UsageStatus {

    USE("Y", "Use"), NOT_USE("N", "NotUse"), DELETE("D", "Deleted");

    private final String code;
    private final String value;


}
