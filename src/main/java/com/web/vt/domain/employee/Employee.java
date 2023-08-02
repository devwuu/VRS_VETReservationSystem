package com.web.vt.domain.employee;


import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.common.BaseEntity;
import com.web.vt.domain.common.enums.Position;
import com.web.vt.domain.common.enums.PositionConverter;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.common.enums.UsageStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter
@Accessors(fluent = true, chain = true)
@Table(name = "employee")
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Id
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role; // 병원 관리자 권한 OR 일반 근무직원

    @Convert(converter = PositionConverter.class)
    private Position position;

    @JoinColumn(name = "clinic_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VeterinaryClinic clinic;

    @Convert(converter = UsageStatusConverter.class)
    private UsageStatus status;


    public Employee(EmployeeVO vo) {
        id = vo.id();
        password = vo.password();
        role = vo.role();
        position = vo.position();
        status = vo.status();
    }

    public Employee addClinic(Long clinicId){
        clinic = new VeterinaryClinic().id(clinicId);
        return this;
    }
}
