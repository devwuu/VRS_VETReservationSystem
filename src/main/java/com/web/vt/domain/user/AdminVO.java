package com.web.vt.domain.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import com.web.vt.domain.common.enums.UsageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class AdminVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8910905059888155635L;

    private String id;
    private String password;
    private UsageStatus status;

    public AdminVO(Admin admin) {
        id = admin.id();
        password = admin.password();
        status = admin.status();
    }
}
