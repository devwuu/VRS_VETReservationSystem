package com.web.vt.domain.reservation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Setter @Getter
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReservationSlotDTO implements Serializable {

    private static final long serialVersionUID = 570279679849054276L;

    private boolean available;
    private Instant slotTime;

}
