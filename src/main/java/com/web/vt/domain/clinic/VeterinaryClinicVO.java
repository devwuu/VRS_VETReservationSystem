package com.web.vt.domain.clinic;

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
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VeterinaryClinicVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8369405666042097838L;

    private Long id;
    private String name;
    private String contact;
    private String remark;
    private UsageStatus status;

    public VeterinaryClinicVO(VeterinaryClinic entity){
        id = entity.id();
        name = entity.name();
        contact = entity.contact();
        remark = entity.remark();
        status = entity.status();
        updatedAt(entity.updatedAt());
        updatedBy(entity.updatedBy());
        createdAt(entity.createdAt());
        createBy(entity.createdBy());
    }

}
