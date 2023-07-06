package com.web.vt.domain.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.web.vt.domain.common.enums.UsageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
public class AnimalGuardianDTO {

    // animal
    private Long animalId;
    private String animalName;
    private String animalSpecies;
    private Long animalAge;
    private String animalRemark;
    private UsageStatus animalStatus;

    // guardian
    private Long guardianId;
    private String guardianName;
    private String guardianContact;
    private String guardianAddress;
    private UsageStatus guardianStatus;
    private String guardianRemark;

    @QueryProjection
    public AnimalGuardianDTO(Long animalId,
                             String animalName,
                             String animalSpecies,
                             Long animalAge,
                             String animalRemark,
                             UsageStatus animalStatus,
                             Long guardianId,
                             String guardianName,
                             String guardianContact,
                             String guardianAddress,
                             UsageStatus guardianStatus,
                             String guardianRemark) {

        this.animalId = animalId;
        this.animalName = animalName;
        this.animalSpecies = animalSpecies;
        this.animalAge = animalAge;
        this.animalRemark = animalRemark;
        this.animalStatus = animalStatus;
        this.guardianId = guardianId;
        this.guardianName = guardianName;
        this.guardianContact = guardianContact;
        this.guardianAddress = guardianAddress;
        this.guardianStatus = guardianStatus;
        this.guardianRemark = guardianRemark;
    }
}
