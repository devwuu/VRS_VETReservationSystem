package com.web.vt.domain.animal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class AnimalVO extends BaseEntity {

    private Long id;
    private String species;
    private Long age;
    private String remark;
    private Long clinicId;

/*    public AnimalVO addClinic(){

    }*/

}
