package com.web.vt.domain.user;

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
class AdminRepositoryTest {

    @Autowired
    AdminRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test @DisplayName("테스트용 admin user를 생성합니다")
    public void save() {
        AdminVO vo = new AdminVO()
                .id("test")
                .password(passwordEncoder.encode("1234"))
                .status(UsageStatus.USE);
        Admin saved = repository.save(new Admin(vo));
        AdminVO result = new AdminVO(saved);
        assertThat(result.id()).isEqualTo(vo.id());
    }

}