package com.web.vt.security.redis;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.TimeUnit;

@RedisHash(value = "refresh")
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class UserRefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

    public UserRefreshToken(UserDetails userDetails, String refreshToken, Long expiration){
        this.id = userDetails.getUsername();
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

}
