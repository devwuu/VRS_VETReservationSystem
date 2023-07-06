package com.web.vt.domain.reservation;

import com.web.vt.domain.animal.Animal;
import com.web.vt.domain.animal.AnimalVO;
import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.BaseEntity;
import com.web.vt.domain.common.enums.ReservationStatus;
import com.web.vt.domain.common.enums.ReservationStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity @Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "reservation_date_time")
    private Instant reservationDateTime;

    @Convert(converter = ReservationStatusConverter.class)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "remark")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private VeterinaryClinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public Reservation(ReservationVO vo) {
        id = vo.id();
        reservationDateTime = vo.reservationDateTime();
        status = vo.status();
        remark = vo.remark();
    }

    public Reservation addClinic(VeterinaryClinicVO vo) {
        clinic = new VeterinaryClinic(vo);
        return this;
    }

    public Reservation addAnimal(AnimalVO vo) {
        animal = new Animal(vo);
        return this;
    }
}
