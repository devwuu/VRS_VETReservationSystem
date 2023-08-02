package com.web.vt.domain.user;


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
@Table(name = "admin")
@NoArgsConstructor
public class Admin extends BaseEntity {
    // 시스템 관리자

    @Id
    private String id;

    @Column(name = "password")
    private String password;

    @Convert(converter = UsageStatusConverter.class)
    private UsageStatus status;


    public Admin(AdminVO vo) {
        id = vo.id();
        password = vo.password();
        status = vo.status();
    }
}
