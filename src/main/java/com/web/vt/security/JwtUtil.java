package com.web.vt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties properties;
    private final UserRefreshTokenRepository tokenRepository;
    private final BlacklistRepository blacklistRepository;

    public DecodedJWT decodeToken(String token){

        if(isStartWithPrefix(token)){
            token = removePrefix(token);
        }

        DecodedJWT decodedJWT = JWT
                .require(properties.getSign())
                .build()
                .verify(token);

        return decodedJWT;
    }

    public String generateAccessToken(UserDetails userDetails) {
        String token = createToken(userDetails, properties.getAccessTokenSubject(), properties.getAccessTokenExpiredAt());
        return token;
    }

    @Transactional
    public String generateRefreshToken(UserDetails userDetails){
        String token = createToken(userDetails, properties.getRefreshTokenSubject(), properties.getRefreshTokenExpiredAt());
        UserRefreshToken refreshToken = new UserRefreshToken(userDetails, token, (long)properties.getRefreshTokenExpiredTime());
        tokenRepository.save(refreshToken);
        return token;
    }

    @Transactional(readOnly = true)
    public Optional<DecodedJWT> verifyRefreshToken(String token){
        DecodedJWT decodedJWT = decodeToken(token);
        Optional<UserRefreshToken> refreshToken = tokenRepository.findById(decodedJWT.getClaim("id").asString());
        return refreshToken.filter(userRefreshToken -> userRefreshToken.refreshToken().equals(token)).map(userRefreshToken -> decodedJWT);
    }

    @Transactional(readOnly = true)
    public Optional<DecodedJWT> verifyAccessToken(String header){
        String token = removePrefix(header);
        DecodedJWT decodedJWT = decodeToken(token);
        Optional<Blacklist> blacklist = blacklistRepository.findById(decodedJWT.getClaim("id").asString());
        return blacklist.filter(black -> black.accessToken().equals(token)).map(black -> Optional.<DecodedJWT>empty()).orElse(Optional.of(decodedJWT));
    }

    @Transactional
    public void destroyToken(String header){

        String accessToken = removePrefix(header);
        DecodedJWT decodedJWT = decodeToken(accessToken);
        String username = decodedJWT.getClaim("id").asString();
        long expiration = Duration.between(Instant.now(), decodedJWT.getExpiresAtAsInstant()).toMinutes();

        tokenRepository.deleteById(username);

        Blacklist blacklist = new Blacklist()
                .id(username)
                .accessToken(accessToken)
                .expiration(expiration);

        blacklistRepository.save(blacklist);
    }

    private String createToken(UserDetails userDetails, String subject, Instant expiration){

        String token = JWT.create()
                .withSubject(subject)
                .withIssuer(properties.getIssuer())
                .withClaim("id", userDetails.getUsername())
                .withExpiresAt(expiration)
                .sign(properties.getSign());

        return token;
    }

    public Boolean isStartWithPrefix(String header){
        return StringUtil.startsWith(header, properties.getPrefix());
    }

    public String removePrefix(String header){
        return StringUtil.remove(header, properties.getPrefix());
    }

    public Boolean isAccessToken(String header){
        return decodeToken(header).getSubject().equals(properties.getAccessTokenSubject());
    }


}

