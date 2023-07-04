package com.web.vt.domain.reservation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.web.vt.domain.common.BaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Getter @Setter
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReservationVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4182200220263111565L;

    private Long id;
    private Long clinicId;
    private Long animalId;
    private Instant reservationDateTime;
    private String status; // Cancel, Confirm
    private String remark;

/*    public ReservationVO addClinic(){

    }

    public ReservationVO addAnimal(){

    }*/


}
