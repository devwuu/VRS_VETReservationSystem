package com.web.vt.domain.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationManagementServiceTest {

    @Autowired
    private ReservationManagementService service;

    @Test
    public void saveTest() {

        ZonedDateTime start = LocalDateTime.now().atZone(ZoneOffset.UTC);
        ZonedDateTime end = start.plusDays(1);

        ReservationManagementVO vo = new ReservationManagementVO()
                .clinicId(2L)
                .startDateTime(start.toInstant())
                .endDateTime(end.toInstant());

        ReservationManagementVO save = service.save(vo);
        assertThat(save.id()).isNotNull();
    }

}