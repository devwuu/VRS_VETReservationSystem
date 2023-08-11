package com.web.vt.security;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -1695490485907383846L;

    private String id;
    private String password;
    private String refreshToken;

}
