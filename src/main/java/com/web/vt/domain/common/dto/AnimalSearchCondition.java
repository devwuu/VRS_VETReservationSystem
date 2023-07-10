package com.web.vt.domain.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(fluent = true, chain = true)
public class AnimalSearchCondition {

    private String animalName;
    private String guardianName;

}
