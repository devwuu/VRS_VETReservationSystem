package com.web.vt.domain.animal;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.BaseEntity;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.common.enums.UsageStatusConverter;
import com.web.vt.domain.guardian.Guardian;
import com.web.vt.domain.guardian.GuardianVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class Animal extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "age")
    private Long age;

    @Column(name = "remark")
    private String remark;

    @Convert(converter = UsageStatusConverter.class)
    private UsageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", updatable = false)
    private VeterinaryClinic clinic;

    public Animal(AnimalVO vo) {
        id = vo.id();
        name = vo.name();
        species = vo.species();
        age = vo.age();
        status = vo.status();
        remark = vo.remark();
    }

    public Animal addClinic(VeterinaryClinicVO vo) {
        clinic = new VeterinaryClinic(vo);
        return this;
    }

    public Animal addGuardian(GuardianVO vo) {
        guardian = new Guardian(vo);
        return this;
    }
}
