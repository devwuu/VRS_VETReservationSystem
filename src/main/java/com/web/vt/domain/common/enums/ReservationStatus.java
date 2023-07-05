package com.web.vt.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum ReservationStatus {

    APPROVED("A", "Approved"), REVOKED("R", "Revoked");

    private final String code;
    private final String value;

}
