package com.web.vt.domain.reservation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.clinic.VeterinaryClinic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class ReservationManagementVO {

    private Long id;
    private Long clinicId;
    private Instant startDateTime;
    private Instant endDateTime;

    public ReservationManagementVO(ReservationManagement entity) {
        id = entity.id();
        startDateTime = entity.startDateTime();
        endDateTime = entity.endDateTime();
    }

    public ReservationManagementVO addClinic(VeterinaryClinic entity){
        clinicId = entity.id();
        return this;
    }
}
