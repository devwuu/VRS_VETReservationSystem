package com.web.vt.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.querydsl.core.annotations.QueryProjection;
import com.web.vt.domain.common.enums.Position;
import com.web.vt.domain.common.enums.UsageStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EmployeeClinicDTO implements Serializable {

    private static final long serialVersionUID = 4146695139922760801L;

    //employee
    private Long employeeId;
    private String employeeLoginId;
    private String employeePassword;
    private String employeeRole;
    private Position employeePosition;
    private UsageStatus employeeStatus;

    //clinic
    private Long clinicId;
    private String clinicName;


    @QueryProjection
    public EmployeeClinicDTO(Long employeeId,
                             String employeeLoginId,
                             String employeePassword,
                             String employeeRole,
                             Position employeePosition,
                             UsageStatus employeeStatus,
                             Long clinicId,
                             String clinicName) {
        this.employeeId = employeeId;
        this.employeeLoginId = employeeLoginId;
        this.employeePassword = employeePassword;
        this.employeeRole = employeeRole;
        this.employeePosition = employeePosition;
        this.employeeStatus = employeeStatus;
        this.clinicId = clinicId;
        this.clinicName = clinicName;
    }
}
