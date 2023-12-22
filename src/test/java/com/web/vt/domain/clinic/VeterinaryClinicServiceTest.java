package com.web.vt.domain.clinic;

import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest
@Transactional
class VeterinaryClinicServiceTest {

    @Autowired
    VeterinaryClinicService service;

    @Test @DisplayName("새로운 동물병원을 등록합니다.")
    public void saveTest() {
        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .name("완전 새로운 동물병원")
                .contact("0211112222")
                .remark("고양이 전문")
                .status(UsageStatus.USE);
        VeterinaryClinicVO saved = service.save(vo);
        assertThat(saved.id()).isNotNull();
    }

    @Test @DisplayName("동물병원 정보를 수정합니다.")
    public void updateTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(302L)
                .name("수정된 고양이 동물병원")
                .status(UsageStatus.USE)
                .contact("03111112222")
                .remark("수정됨");
        VeterinaryClinicVO update = service.update(vo);

        assertThat(vo.id()).isEqualTo(update.id());
    }

    @Test @DisplayName("동물병원을 삭제합니다.")
    public void deleteTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(302L);
        VeterinaryClinicVO delete = service.delete(vo);

        assertThat(delete.status()).isEqualTo(UsageStatus.DELETE);

    }

    @Test @DisplayName("영업중인 특정 동물병원을 찾습니다.")
    public void findByIdAnsStatusTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(202L)
                .status(UsageStatus.USE);
        VeterinaryClinicVO find = service.findByIdAndStatus(vo);
        assertThat(find.id()).isEqualTo(vo.id());

    }

    @Test @DisplayName("특정 동물병원을 찾습니다.")
    public void findByIdTest() {
        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(202L);
        VeterinaryClinicVO find = service.findById(vo);
        assertThat(find.id()).isEqualTo(vo.id());
    }

    @Test @DisplayName("삭제되지 않은 모든 동물병원을 가져옵니다.")
    public void findAllByStatusInTest() {
        Pageable pageable = PageRequest.of(0, 2, by(Order.desc("createdAt")));
        Page<VeterinaryClinicVO> result = service.findAllByStatusIn(pageable, UsageStatus.USE, UsageStatus.NOT_USE);
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
    }



}