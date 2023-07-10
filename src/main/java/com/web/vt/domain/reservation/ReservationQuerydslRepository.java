package com.web.vt.domain.reservation;

import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationQuerydslRepository {

    ReservationAnimalGuardianDTO findByIdWithAnimalAndGuardian(ReservationVO vo);
    Page<ReservationAnimalGuardianDTO> findAllWithAnimalAndGuardian(Long clinicId, Pageable pageable);

}
