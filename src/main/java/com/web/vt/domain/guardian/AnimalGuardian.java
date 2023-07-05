package com.web.vt.domain.guardian;

import com.web.vt.domain.common.BaseEntity;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.common.enums.UsageStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "animal_guardian")
@Getter @Setter @NoArgsConstructor
@Accessors(fluent = true, chain = true)
public class AnimalGuardian extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Convert(converter = UsageStatusConverter.class)
    @Column(name = "status")
    private UsageStatus status;

    @Column(name = "remark")
    private String remark;

    public AnimalGuardian(AnimalGuardianVO vo) {
        id = vo.id();
        name = vo.name();
        contact = vo.contact();
        address = vo.address();
        status = vo.status();
        remark = vo.remark();
    }
}
