package com.web.vt.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
@Accessors(fluent = true)
public enum Position {

    VET("V", "VET"), Technician("T", "Technician");

    private final String code;
    private final String value;


}
