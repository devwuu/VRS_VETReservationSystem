package com.web.vt.domain.reservation;

import com.web.vt.domain.animal.Animal;
import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.common.BaseEntity;
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

    @Column(name = "status")
    private String status; // Cancel, Confirm

    @Column(name = "remark")
    private String remark;

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private VeterinaryClinic clinic;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

}
