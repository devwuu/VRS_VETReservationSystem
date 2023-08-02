package com.web.vt.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtProperties {

    SECRET("sample"),
    EXPIRED_MIN("10"),
    PRE_FIX("Bearer ");

    private final String value;

}
