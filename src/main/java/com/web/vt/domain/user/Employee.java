package com.web.vt.domain.user;


import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.common.BaseEntity;
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
    private String role;

    @JoinColumn(name = "clinic_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VeterinaryClinic clinic;

    @Convert(converter = UsageStatusConverter.class)
    private UsageStatus status;


}
