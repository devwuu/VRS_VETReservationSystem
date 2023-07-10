package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnimalGuardianServiceTest {

    @Autowired
    private GuardianService service;

    @Test @DisplayName("새로운 보호자를 등록합니다.")
    public void saveTest() {

        GuardianVO vo = new GuardianVO()
                .name("devwuu")
                .contact("123-123")
                .status(UsageStatus.USE);

        GuardianVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();

    }

    @Test @DisplayName("등록된 보호자를 수정합니다.")
    public void updateTest() {

        GuardianVO vo = new GuardianVO()
                .id(52L).address("서울시");
        GuardianVO updated = service.update(vo);
        assertThat(vo.id()).isEqualTo(updated.id());
    }

    @Test @DisplayName("등록되지 않은 보호자를 수정하면 Exception이 발생합니다.")
    public void updateTest1() {

        GuardianVO vo = new GuardianVO()
                .id(99L).address("제주도");
        Assertions.assertThrows(NotFoundException.class, () -> {
            service.update(vo);
        }, "예외가 발생하지 않았습니다,");
    }

    @Test @DisplayName("등록된 보호자를 조회합니다")
    public void findByIdTest() {
        GuardianVO vo = new GuardianVO().id(152L).status(UsageStatus.USE);
        GuardianVO find = service.findByIdAndStatus(vo);
        assertThat(find.id()).isEqualTo(vo.id());
    }

    @Test @DisplayName("등록된 보호자를 삭제합니다")
    public void deleteTest() {

        GuardianVO vo = new GuardianVO().id(202L);
        GuardianVO delete = service.delete(vo);
        assertThat(delete.status()).isEqualTo(UsageStatus.DELETE);

    }

}