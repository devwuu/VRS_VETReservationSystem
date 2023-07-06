package com.web.vt.domain.common.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.web.vt.domain.common.dto.QAnimalGuardianDTO is a Querydsl Projection type for AnimalGuardianDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAnimalGuardianDTO extends ConstructorExpression<AnimalGuardianDTO> {

    private static final long serialVersionUID = -1237139231L;

    public QAnimalGuardianDTO(com.querydsl.core.types.Expression<Long> animalId, com.querydsl.core.types.Expression<String> animalName, com.querydsl.core.types.Expression<String> animalSpecies, com.querydsl.core.types.Expression<Long> animalAge, com.querydsl.core.types.Expression<String> animalRemark, com.querydsl.core.types.Expression<com.web.vt.domain.common.enums.UsageStatus> animalStatus, com.querydsl.core.types.Expression<Long> guardianId, com.querydsl.core.types.Expression<String> guardianName, com.querydsl.core.types.Expression<String> guardianContact, com.querydsl.core.types.Expression<String> guardianAddress, com.querydsl.core.types.Expression<com.web.vt.domain.common.enums.UsageStatus> guardianStatus, com.querydsl.core.types.Expression<String> guardianRemark) {
        super(AnimalGuardianDTO.class, new Class<?>[]{long.class, String.class, String.class, long.class, String.class, com.web.vt.domain.common.enums.UsageStatus.class, long.class, String.class, String.class, String.class, com.web.vt.domain.common.enums.UsageStatus.class, String.class}, animalId, animalName, animalSpecies, animalAge, animalRemark, animalStatus, guardianId, guardianName, guardianContact, guardianAddress, guardianStatus, guardianRemark);
    }

}

