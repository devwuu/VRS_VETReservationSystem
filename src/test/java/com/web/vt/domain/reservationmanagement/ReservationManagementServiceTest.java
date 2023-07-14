package com.web.vt.domain.reservationmanagement;

import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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

    @Test @DisplayName("예약 관리 id로 예약 관리 정보를 가져옵니다")
    public void findByIdTest() {
        ReservationManagementVO vo = new ReservationManagementVO().id(152L);
        ReservationManagementVO find = service.findById(vo);
        assertThat(find.id()).isEqualTo(vo.id());
    }

    @Test @DisplayName("병원 id로 예약 관리 정보를 가져옵니다")
    public void findByClinicId() {
        ReservationManagementVO vo = new ReservationManagementVO().clinicId(2L);
        ReservationManagementVO find = service.findByClinicId(vo);
        assertThat(vo.clinicId()).isEqualTo(find.clinicId());
    }

    @Test @Disabled
    @DisplayName("예약 관리 정보를 신규 등록합니다.")
    public void saveTest() {

        ReservationManagementVO vo = new ReservationManagementVO()
                .clinicId(102L)
                .status(UsageStatus.USE)
                .startDateTime(START.toInstant())
                .endDateTime(END.toInstant());

        ReservationManagementVO save = service.save(vo);
        assertThat(save.id()).isNotNull();
    }

    @Test @DisplayName("예약 관리 정보를 수정합니다.")
    public void updateTest() {

        ReservationManagementVO vo = new ReservationManagementVO()
                .id(152L)
                .clinicId(102L)
                .status(UsageStatus.NOT_USE);

        ReservationManagementVO saved = service.update(vo);
        assertThat(saved.clinicId()).isEqualTo(vo.clinicId());

    }

}