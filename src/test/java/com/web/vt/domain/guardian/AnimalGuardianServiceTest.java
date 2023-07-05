package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnimalGuardianServiceTest {

    @Autowired
    private AnimalGuardianService service;

    @Test @DisplayName("새로운 보호자를 등록합니다.")
    public void saveTest() {

        AnimalGuardianVO vo = new AnimalGuardianVO()
                .name("devwuu")
                .contact("123-123")
                .status(UsageStatus.USE);

        AnimalGuardianVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();

    }

    @Test @DisplayName("등록된 보호자를 수정합니다.")
    public void updateTest() {

        AnimalGuardianVO vo = new AnimalGuardianVO()
                .id(52L).address("서울시");
        AnimalGuardianVO updated = service.update(vo);
        assertThat(vo.id()).isEqualTo(updated.id());
    }

    @Test @DisplayName("등록된 보호자를 조회합니다")
    public void findByIdTest() {
        AnimalGuardianVO vo = new AnimalGuardianVO().id(152L);
        AnimalGuardianVO find = service.findById(vo);
        assertThat(find.id()).isEqualTo(vo.id());
    }

}