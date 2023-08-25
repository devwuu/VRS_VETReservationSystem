package com.web.vt.domain.guardian;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import com.web.vt.domain.common.enums.UsageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GuardianVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2010468697125940197L;

    private Long id;
    private Long clinicId;
    private String name;
    private String contact;
    private String address;
    private UsageStatus status;
    private String remark;


    public GuardianVO(Guardian entity) {
        id = entity.id();
        name = entity.name();
        contact = entity.contact();
        address = entity.address();
        remark = entity.remark();
        status = entity.status();
        createBy(entity.createdBy());
        createdAt(entity.createdAt());
        updatedBy(updatedBy());
        updatedAt(entity.updatedAt());
    }

}
