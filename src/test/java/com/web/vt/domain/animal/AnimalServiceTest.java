package com.web.vt.domain.animal;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.guardian.AnimalGuardianVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnimalServiceTest {

    @Autowired
    private AnimalService service;

    @Test @DisplayName("새로운 반려동물을 등록합니다.")
    public void saveTest() {
        AnimalVO vo = new AnimalVO().clinicId(1L)
                .age(10L)
                .species("강아지")
                .name("달이")
                .guardian(new AnimalGuardianVO().id(1L));
        AnimalVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();
    }

    @Test @DisplayName("식별자가 있는 entity의 경우, save()를 했을 때 select를 하고 update를 한다.")
    public void saveTest1() {
        AnimalVO vo = new AnimalVO().clinicId(2L)
                .id(1L)
                .age(3L)
                .species("강아지")
                .name("달이");
        AnimalVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();
    }

    @Test @DisplayName("반려동물의 정보를 업데이트 합니다.")
    public void updateTest() {
        AnimalVO vo = new AnimalVO().id(1L).name("샛별");
        AnimalVO updated = service.update(vo);
        assertThat(vo.id()).isEqualTo(updated.id());
    }

    @Test @DisplayName("반려동물의 정보를 삭제합니다")
    public void deleteTest() {
        AnimalVO vo = new AnimalVO().id(2L);
        AnimalVO find = service.delete(vo);
        assertThat(find.status()).isEqualTo(UsageStatus.DELETE);
    }

}