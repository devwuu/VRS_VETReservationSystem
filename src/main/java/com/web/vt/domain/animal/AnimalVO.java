package com.web.vt.domain.animal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import com.web.vt.domain.guardian.AnimalGuardian;
import com.web.vt.domain.guardian.AnimalGuardianVO;
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
    private Long clinicId;
    private AnimalGuardianVO guardian;

    public AnimalVO(Animal entity) {
        id = entity.id();
        name = entity.name();
        species = entity.species();
        age = entity.age();
        remark = entity.remark();
        updatedBy(entity.updatedBy());
        updatedAt(entity.updatedAt());
        createBy(entity.createdBy());
        createdAt(entity.createdAt());
    }

    public AnimalVO addGuardian(AnimalGuardian guardian){
        this.guardian = new AnimalGuardianVO(guardian);
        return this;
    }

}
