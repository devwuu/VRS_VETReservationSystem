package com.web.vt.domain.reservation;

import com.web.vt.domain.common.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    ReservationService service;

    @Test @DisplayName("새로운 예약을 등록합니다")
    public void saveReservationTest() {
        ReservationVO vo = new ReservationVO()
                .clinicId(202L)
                .animalId(1L)
                .status(ReservationStatus.APPROVED)
                .reservationDateTime(Instant.now());
        ReservationVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();
    }

    @Test
    @DisplayName("특정 예약건을 조회합니다.")
    public void findByIdTest() {
        ReservationVO vo = new ReservationVO().id(152L);
        ReservationVO find = service.findById(vo);
        assertThat(vo.id()).isEqualTo(find.id());
    }

}