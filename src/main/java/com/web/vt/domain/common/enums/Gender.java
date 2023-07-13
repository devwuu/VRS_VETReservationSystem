package com.web.vt.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum Gender {

    Male("M", "Male"), Female("F", "Female"), ETC("E", "ETC");

    private final String code;
    private final String value;
}
