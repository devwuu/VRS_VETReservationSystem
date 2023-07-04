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

    private final ZonedDateTime START = LocalDateTime.now().atZone(ZoneOffset.UTC);
    private final ZonedDateTime END = START.plusDays(1);

    @Test
    public void saveTest() {

        ReservationManagementVO vo = new ReservationManagementVO()
                .clinicId(2L)
                .startDateTime(START.toInstant())
                .endDateTime(END.toInstant());

        ReservationManagementVO save = service.save(vo);
        assertThat(save.id()).isNotNull();
    }

    @Test
    public void updateTest() {

        ReservationManagementVO vo = new ReservationManagementVO()
                .id(1L)
                .clinicId(2L)
                .startDateTime(START.toInstant());

        ReservationManagementVO saved = service.update(vo);
        assertThat(saved.clinicId()).isEqualTo(vo.clinicId());

    }

}