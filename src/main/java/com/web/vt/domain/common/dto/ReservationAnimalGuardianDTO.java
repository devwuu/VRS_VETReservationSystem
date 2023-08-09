package com.web.vt.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.querydsl.core.annotations.QueryProjection;
import com.web.vt.domain.common.enums.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReservationAnimalGuardianDTO implements Serializable {

    private static final long serialVersionUID = 4821987143308535602L;

    // reservation
    private Long reservationId;
    private Instant reservationDateTime;
    private ReservationStatus reservationStatus;
    private Long reservationClinicId;

    // animal
    private Long animalId;
    private String animalName;
    private String animalSpecies;
    private Long animalAge;

    //guardian
    private Long guardianId;
    private String guardianName;
    private String guardianContact;

    @QueryProjection
    public ReservationAnimalGuardianDTO(Long reservationId,
                                        Instant reservationDateTime,
                                        ReservationStatus reservationStatus,
                                        Long reservationClinicId,
                                        Long animalId,
                                        String animalName,
                                        String animalSpecies,
                                        Long animalAge,
                                        Long guardianId,
                                        String guardianName,
                                        String guardianContact) {
        this.reservationId = reservationId;
        this.reservationDateTime = reservationDateTime;
        this.reservationStatus = reservationStatus;
        this.reservationClinicId = reservationClinicId;
        this.animalId = animalId;
        this.animalName = animalName;
        this.animalSpecies = animalSpecies;
        this.animalAge = animalAge;
        this.guardianId = guardianId;
        this.guardianName = guardianName;
        this.guardianContact = guardianContact;
    }
}
