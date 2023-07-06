package com.web.vt.domain.reservation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import com.web.vt.domain.common.enums.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReservationVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4182200220263111565L;

    private Long id;
    private Long clinicId;
    private Long animalId;
    private Instant reservationDateTime;
    private ReservationStatus status;
    private String remark;

    public ReservationVO(Reservation entity) {
        id = entity.id();
        reservationDateTime = entity.reservationDateTime();
        status = entity.status();
        remark = entity.remark();
    }


}
