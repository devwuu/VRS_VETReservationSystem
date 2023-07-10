package com.web.vt.domain.animal;

import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.AnimalSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalQuerydslRepository {

    AnimalGuardianDTO findByIdWithGuardian(AnimalVO vo);

    Page<AnimalGuardianDTO> findAllWithGuardian(Long clinicId, Pageable pageable);

    Page<AnimalGuardianDTO> searchAllWithGuardian(Long clinicId, AnimalSearchCondition condition, Pageable pageable);

}
