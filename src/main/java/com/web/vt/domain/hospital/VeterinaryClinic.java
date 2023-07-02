package com.web.vt.domain.hospital;

import com.web.vt.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity @Table(name = "veterinary_clinic")
@Getter @Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class VeterinaryClinic extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "remark")
    private String remark;

    public VeterinaryClinic(VeterinaryClinicVO vo){
        id = vo.id();
        name = vo.name();
        contact = vo.contact();
        remark = vo.remark();
    }


}
