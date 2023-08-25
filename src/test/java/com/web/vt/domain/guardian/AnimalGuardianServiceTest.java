package com.web.vt.domain.guardian;

import com.web.vt.domain.common.dto.GuardianSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
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
class AnimalGuardianServiceTest {

    @Autowired
    private GuardianService service;

    @Test @DisplayName("새로운 보호자를 등록합니다.")
    public void saveTest() {

        GuardianVO vo = new GuardianVO()
                .clinicId(202L)
                .name("devwuu")
                .contact("03111112222")
                .status(UsageStatus.USE)
                .remark("달이 보호자님");

        GuardianVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();

    }

    @Test @DisplayName("등록된 보호자를 수정합니다.")
    public void updateTest() {

        GuardianVO vo = new GuardianVO()
                .id(352L)
                .name("devwuu")
                .contact("03111112222")
                .address("서울시")
                .status(UsageStatus.USE);
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

    @Test @DisplayName("사용중이면서 등록된 특정 보호자를 조회합니다")
    public void findByIdTest() {
        GuardianVO vo = new GuardianVO().id(152L).status(UsageStatus.USE);
        GuardianVO find = service.findByIdAndStatus(vo);
        assertThat(find.id()).isEqualTo(vo.id());
    }

    @Test @DisplayName("사용중이면서 등록된 보호자 리스트를 조회합니다")
    public void findAllTest() {
        GuardianVO vo = new GuardianVO().clinicId(202L).status(UsageStatus.USE);
        Pageable pageable = (Pageable) PageRequest.of(0, 5, by(desc("createdAt")));
        Page<GuardianVO> all = service.findAllByClinicAndStatus(vo, pageable);
        assertThat(all).isNotNull();
    }

    @Test @DisplayName("사용중이면서 등록된 보호자 리스트를 검색합니다")
    public void searhAllTest() {
        Pageable pageable = (Pageable) PageRequest.of(0, 5);
        GuardianSearchCondition condition = new GuardianSearchCondition()
                .setClinicId(202L)
                .setName("bc");
        Page<GuardianVO> all = service.searchAll(condition, pageable);
        assertThat(all).isNotNull();
    }

    @Test @DisplayName("등록된 보호자를 삭제합니다")
    public void deleteTest() {

        GuardianVO vo = new GuardianVO().id(352L);
        GuardianVO delete = service.delete(vo);
        assertThat(delete.status()).isEqualTo(UsageStatus.DELETE);

    }

}