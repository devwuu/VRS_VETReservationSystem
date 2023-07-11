package com.web.vt.domain.reservation;

import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationSearchCondition;
import com.web.vt.domain.common.enums.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

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
        ReservationAnimalGuardianDTO find = service.findByIdWithAnimalAndGuardian(vo);
        assertThat(vo.id()).isEqualTo(find.reservationId());
    }

    @Test @DisplayName("등록된 전체 예약을 조회합니다.")
    public void findAllTest() {
        Long clinicId = 202L;
        Pageable pageable = PageRequest.of(0, 1, by(desc("createdAt")));
        Page<ReservationAnimalGuardianDTO> find = service.findAllWithAnimalAndGuardian(clinicId, pageable);
        assertThat(find.getSize()).isEqualTo(pageable.getPageSize());
    }

    @Test @DisplayName("에약을 수정합니다.")
    public void updateReservation() {
        ReservationVO vo = new ReservationVO().id(102L).status(ReservationStatus.REVOKED);
        ReservationVO update = service.update(vo);
        assertThat(vo.status()).isEqualTo(update.status());
    }

    @Test @DisplayName("예약을 검색합니다.")
    public void searchTest() {
        LocalDate criteria = LocalDate.of(2023, Month.JULY, 1);
        Instant from = criteria.atStartOfDay().toInstant(ZoneOffset.of("+09:00"));
        Instant to = criteria.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX).toInstant(ZoneOffset.of("+09:00"));

        ReservationSearchCondition condition = new ReservationSearchCondition().from(from).to(to);
        Pageable pageable = PageRequest.of(0, 1, by(desc("createdAt")));
        Page<ReservationAnimalGuardianDTO> search = service.searchAllWithAnimalAndGuardian(202L, condition, pageable);
        assertThat(search.getSize()).isEqualTo(pageable.getPageSize());
    }

}