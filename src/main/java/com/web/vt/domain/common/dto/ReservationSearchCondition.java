package com.web.vt.domain.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Getter @Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ReservationSearchCondition implements Serializable {

    private static final long serialVersionUID = -3761551007912023827L;

    private Instant from;
    private Instant to;
    private String guardianName;
    private String animalName;

}
