package com.web.vt.domain.reservation;

import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationQuerydslRepository {

    ReservationAnimalGuardianDTO findByIdWithAnimalAndGuardian(ReservationVO vo);
    Page<ReservationAnimalGuardianDTO> findAllWithAnimalAndGuardian(Long clinicId, Pageable pageable);
    Page<ReservationAnimalGuardianDTO> searchAllWithAnimalAndGuardian(Long clinicId, ReservationSearchCondition condition, Pageable pageable);
    List<ReservationSlotDTO> findAllByReservationTime(Long clinicId, ReservationSearchCondition condition);

}
