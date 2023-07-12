package com.web.vt.domain.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Setter @Getter
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
public class ReservationSlotDTO {

    private boolean available;
    private Instant slotTime;

}
