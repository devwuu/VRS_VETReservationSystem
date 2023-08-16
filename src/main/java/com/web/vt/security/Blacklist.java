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

    private String accessToken;
    /**
     * refresh token의 경우 UserRefreshToken으로 관리/검증되기 때문에
     * Access token만 관리하면 된다
     * */

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

}
