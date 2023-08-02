package com.web.vt.domain.employee;

import com.web.vt.domain.common.enums.Position;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test @DisplayName("테스트용 client user를 생성합니다.")
    public void save() {

        EmployeeVO vo = new EmployeeVO()
                .loginId("test")
                .password(passwordEncoder.encode("1234"))
                .role("ROLE_ADMIN")
                .status(UsageStatus.USE)
                .position(Position.VET);

        Employee save = repository.save(new Employee(vo).addClinic(1L));

        EmployeeVO result = new EmployeeVO(save);

        assertThat(result.loginId()).isEqualTo(vo.loginId());

    }


}