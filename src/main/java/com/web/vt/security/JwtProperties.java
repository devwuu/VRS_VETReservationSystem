package com.web.vt.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JwtProperties {

    private static final String SECRET = "sample";
    private static final int LIMIT = 10;

    public static final String ADMIN_TOKEN = "admin";
    public static final String CLIENT_TOKEN = "client";
    public static final String PRE_FIX = "Bearer ";
    public static final Instant EXPIRED_TIME = LocalDateTime.now().plusMinutes(LIMIT).toInstant(ZoneOffset.UTC);
    public static final Algorithm SIGN = Algorithm.HMAC256(SECRET);



}
