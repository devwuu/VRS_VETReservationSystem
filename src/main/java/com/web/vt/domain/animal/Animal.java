package com.web.vt.domain.animal;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter
@Accessors(chain = true, fluent = true)
public class Animal extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "species")
    private String species;

    @Column(name = "age")
    private Long age;

    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "clinic_id", updatable = false)
    private VeterinaryClinic clinic;

}
