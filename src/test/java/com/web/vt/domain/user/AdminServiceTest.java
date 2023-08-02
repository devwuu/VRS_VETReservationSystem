package com.web.vt.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    AdminService service;

    @Test
    @DisplayName("id로 등록된 admin user를 찾습니다")
    public void findById() {
        AdminVO find = service.findById("test");
        assertThat(find.id()).isEqualTo("test");
    }

}