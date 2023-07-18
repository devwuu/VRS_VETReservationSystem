package com.web.vt.domain.animal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import com.web.vt.domain.common.enums.Gender;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.guardian.GuardianVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class AnimalVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -7382175167575158446L;

    private Long id;
    private String name;
    private String species;
    private Long age;
    private String remark;
    private Gender gender;
    private UsageStatus status;

    private Long clinicId;
    private Long guardianId;

    public AnimalVO(Animal entity) {
        id = entity.id();
        name = entity.name();
        species = entity.species();
        age = entity.age();
        status = entity.status();
        remark = entity.remark();
        gender = entity.gender();
        updatedBy(entity.updatedBy());
        updatedAt(entity.updatedAt());
        createBy(entity.createdBy());
        createdAt(entity.createdAt());
    }

    // 메서드 유지 검토
    public AnimalVO addGuardianId(GuardianVO vo){
        this.guardianId = vo.id();
        return this;
    }

}
