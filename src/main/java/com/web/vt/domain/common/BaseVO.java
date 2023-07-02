package com.web.vt.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter @Setter
@Accessors(chain = true, fluent = true)
public class BaseVO {

    private Instant createdAt;
    private Instant updatedAt;
    private String createBy;
    private String updatedBy;

}
