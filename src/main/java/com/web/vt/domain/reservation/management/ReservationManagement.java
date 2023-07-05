package com.web.vt.domain.reservation.management;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.BaseEntity;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.common.enums.UsageStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Entity
@Table(name = "reservation_management")
@Getter @Setter
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
public class ReservationManagement extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", updatable = false)
    private VeterinaryClinic clinic;

    @Convert(converter = UsageStatusConverter.class)
    @Column(name = "status")
    private UsageStatus status;

    @Column(name = "start_date_time")
    private Instant startDateTime;

    @Column(name = "end_date_time")
    private Instant endDateTime;

    public ReservationManagement(ReservationManagementVO vo) {
        id = vo.id();
        startDateTime = vo.startDateTime();
        endDateTime = vo.endDateTime();
        status = vo.status();
    }

    public ReservationManagement addClinic(VeterinaryClinicVO vo) {
        clinic = new VeterinaryClinic(vo);
        return this;
    }

}
