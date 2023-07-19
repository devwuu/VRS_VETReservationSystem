package com.web.vt.domain.animal;

import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.AnimalSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest
@Transactional
class AnimalServiceTest {

    @Autowired
    private AnimalService service;

    @Test @DisplayName("새로운 반려동물을 등록합니다.")
    public void saveTest() {
        AnimalVO vo = new AnimalVO().clinicId(1L)
                .age(10L)
                .species("강아지")
                .name("달이")
                .guardianId(1L)
                .status(UsageStatus.USE);
        AnimalVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();
    }

    @Test @DisplayName("반려동물의 정보를 업데이트 합니다.")
    public void updateTest() {
        AnimalVO vo = new AnimalVO()
                .id(1L)
                .name("샛별이")
                .age(12L)
                .status(UsageStatus.USE);
        AnimalVO updated = service.update(vo);
        assertThat(vo.id()).isEqualTo(updated.id());
    }

    @Test @DisplayName("반려동물의 정보를 삭제합니다")
    public void deleteTest() {
        AnimalVO vo = new AnimalVO().id(202L);
        AnimalVO find = service.delete(vo);
        assertThat(find.status()).isEqualTo(UsageStatus.DELETE);
    }
    
    @Test @DisplayName("특정 반려동물의 정보와 보호자 정보를 가져옵니다.")
    public void findByIdWithGuardianTest() {
        AnimalVO vo = new AnimalVO().id(1L);
        AnimalGuardianDTO find = service.findByIdWithGuardian(vo);
        assertThat(find.animalId()).isEqualTo(vo.id());
    }

    @Test @DisplayName("등록된 반려동물 리스트를 보호자 정보화 함께 가져옵니다.")
    public void findAllWithGuardian() {
        Long clinicId = 1L;
        Pageable pageable = PageRequest.of(0, 1, by(desc("createdAt")));
        Page<AnimalGuardianDTO> find = service.findAllWithGuardian(clinicId, pageable);
        assertThat(find.getSize()).isEqualTo(pageable.getPageSize());
    }

    @Test @DisplayName("반려동물 리스트에서 보호자 정보로 검색합니다.")
    public void searchAllByGuardianName() {
        Long clinicId = 1L;
        Pageable pageable = PageRequest.of(0, 2, by(desc("createdAt")));
        AnimalSearchCondition condition = new AnimalSearchCondition().setGuardianName("vw");
        Page<AnimalGuardianDTO> find = service.searchAllWithGuardian(clinicId, condition, pageable);
        assertThat(find.getSize()).isEqualTo(pageable.getPageSize());
    }

    @Test @DisplayName("반려동물 리스트에서 반려동물 정보로 검색합니다.")
    public void searchAllByAnimalName() {
        Long clinicId = 1L;
        Pageable pageable = PageRequest.of(0, 2, by(desc("createdAt")));
        AnimalSearchCondition condition = new AnimalSearchCondition().setAnimalName("달");
        Page<AnimalGuardianDTO> find = service.searchAllWithGuardian(clinicId, condition, pageable);
        assertThat(find.getSize()).isEqualTo(pageable.getPageSize());
    }

}