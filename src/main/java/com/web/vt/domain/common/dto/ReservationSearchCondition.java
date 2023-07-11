package com.web.vt.domain.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter @Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class ReservationSearchCondition {

    private Instant from;
    private Instant to;
    private String guardianName;
    private String animalName;

}
