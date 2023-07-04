package com.web.vt.domain.reservation.management;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class ReservationManagementVO extends BaseVO {

    private Long id;
    private Long clinicId;
    private Instant startDateTime;
    private Instant endDateTime;

    public ReservationManagementVO(ReservationManagement entity) {
        id = entity.id();
        startDateTime = entity.startDateTime();
        endDateTime = entity.endDateTime();
        createBy(entity.createBy());
        createdAt(entity.createdAt());
        updatedBy(entity.updatedBy());
        updatedAt(entity.updatedAt());
    }

/*    public ReservationManagementVO addClinic(VeterinaryClinic entity){
        clinicId = entity.id();
        return this;
    }*/

}
