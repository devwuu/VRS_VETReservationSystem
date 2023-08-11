package com.web.vt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.security.admin.AdminRefreshToken;
import com.web.vt.security.admin.AdminRefreshTokenRepository;
import com.web.vt.security.client.ClientRefreshToken;
import com.web.vt.security.client.ClientRefreshTokenRepository;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;
    private final AdminRefreshTokenRepository adminTokenRepository;
    private final ClientRefreshTokenRepository clientTokenRepository;

    // todo admin refresh token, client refresh token 공통화

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
    public String generateRefreshTokenForAdmin(UserDetails userDetails){
        String token = createToken(userDetails, properties.getRefreshTokenSubject(), properties.getRefreshTokenExpiredAt());
        AdminRefreshToken refreshToken = new AdminRefreshToken(userDetails, token, (long)properties.getRefreshTokenExpiredTime());
        adminTokenRepository.save(refreshToken);
        return token;
    }

    @Transactional
    public String generateRefreshTokenForClient(UserDetails userDetails){
        String token = createToken(userDetails, properties.getRefreshTokenSubject(), properties.getRefreshTokenExpiredAt());
        ClientRefreshToken refreshToken = new ClientRefreshToken(userDetails, token, (long)properties.getRefreshTokenExpiredTime());
        clientTokenRepository.save(refreshToken);
        return token;
    }

    @Transactional(readOnly = true)
    public Optional<DecodedJWT> verifyRefreshTokenForAdmin(String token){
        DecodedJWT decodedJWT = decodeToken(token);
        Optional<AdminRefreshToken> refreshToken = adminTokenRepository.findById(decodedJWT.getClaim("id").asString());
        return refreshToken.filter(adminRefreshToken -> adminRefreshToken.refreshToken().equals(token)).map(adminRefreshToken -> decodedJWT);
    }

    @Transactional(readOnly = true)
    public Optional<DecodedJWT> verifyRefreshTokenForClient(String token){
        DecodedJWT decodedJWT = decodeToken(token);
        Optional<ClientRefreshToken> refreshToken = clientTokenRepository.findById(decodedJWT.getClaim("id").asString());
        return refreshToken.filter(clientRefreshToken -> clientRefreshToken.refreshToken().equals(token)).map(clientRefreshToken -> decodedJWT);
    }

    public Boolean isStartWithPrefix(String header){
        return StringUtil.startsWith(header, properties.getPrefix());
    }


}

