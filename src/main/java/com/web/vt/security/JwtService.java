package com.web.vt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.exceptions.InvalidTokenException;
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
public class JwtService {
    private final JwtProperties properties;
    private final UserRefreshTokenRepository tokenRepository;
    private final BlacklistRepository blacklistRepository;

    private String createToken(UserDetails userDetails, String subject, Instant expiration){

        String token = JWT.create()
                .withSubject(subject)
                .withIssuer(properties.getIssuer())
                .withClaim("id", userDetails.getUsername())
                .withExpiresAt(expiration)
                .sign(properties.getSign());

        return token;
    }

    public DecodedJWT decodeToken(String token){

        if(isStartWithPrefix(token)){
            token = StringUtil.remove(token, properties.getPrefix());
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

    public Boolean isStartWithPrefix(String header){
        return StringUtil.startsWith(header, properties.getPrefix());
    }

    @Transactional
    public String destroyRefreshToken(UserDetails userDetails){

        String username = userDetails.getUsername();

        Optional<UserRefreshToken> findToken = tokenRepository.findById(username);

        if(findToken.isEmpty()){
            throw new InvalidTokenException("NOT EXIST TOKEN");
        }

        String refreshToken = findToken.get().refreshToken();
        long expiration = Duration.between(Instant.now(), decodeToken(refreshToken).getExpiresAtAsInstant()).toMinutes();

        Blacklist blacklist = new Blacklist()
                .id(username)
                .refreshToken(refreshToken)
                .expiration(expiration);

        tokenRepository.deleteById(username);
        blacklistRepository.save(blacklist);

        return refreshToken;
    }


}

