package com.web.vt.domain.animal;

import com.web.vt.domain.common.dto.AnimalGuardianDTO;

public interface AnimalQuerydslRepository {

    AnimalGuardianDTO findByIdWithGuardian(AnimalVO vo);

}
