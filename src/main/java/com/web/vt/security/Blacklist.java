package com.web.vt.security;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("black")
@Getter @Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class Blacklist {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

}
