package com.web.vt.domain.guardian;


import com.web.vt.domain.common.dto.GuardianSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuardianQuerydslRepository {

    Page<GuardianVO> searchAll(GuardianSearchCondition condition, Pageable pageable);

}
