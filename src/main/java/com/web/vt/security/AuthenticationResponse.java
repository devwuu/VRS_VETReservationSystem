package com.web.vt.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 9181222863315822243L;

    private String refreshToken;
    private String accessToken;

}
