package com.web.vt.domain.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(chain = true)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GuardianSearchCondition implements Serializable {

    private static final long serialVersionUID = 722540498432266271L;

    private String name;
    private String contact;
    private Long clinicId;

}
