package com.web.vt.domain.employee;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;
    
    @Test
    @DisplayName("직원 id로 등록된 직원을 조회합니다.")
    public void findById() {
        EmployeeClinicDTO find = service.findById(52L);
        assertThat(find).isNotNull();
    }
    
}