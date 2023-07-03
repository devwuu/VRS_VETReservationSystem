package com.web.vt.domain.clinic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

@SpringBootTest
class VeterinaryClinicServiceTest {

    @Autowired
    VeterinaryClinicService service;

    @Test @DisplayName("동물병원 정보를 수정합니다.")
    public void updateTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(2L)
                .name("수정된 동물병원")
                .remark("updated");
        VeterinaryClinicVO update = service.update(vo);

        assertThat(vo.id()).isEqualTo(update.id());
    }

    @Test @DisplayName("동물병원을 삭제합니다.")
    public void deleteTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(502L);
        VeterinaryClinicVO delete = service.delete(vo);

        assertThat(delete.status()).isEqualTo("D");

    }

    @Test @DisplayName("영업중인 특정 동물병원을 찾습니다.")
    public void findTest() {

        VeterinaryClinicVO vo = new VeterinaryClinicVO()
                .id(52L)
                .status("Y");
        VeterinaryClinicVO find = service.findByIdAndStatus(vo);
        assertThat(find.id()).isEqualTo(vo.id());

    }

    @Test @DisplayName("영업 중인 모든 동물병원을 가져옵니다.")
    public void findAllTest() {
        Pageable pageable = PageRequest.of(0, 2, by(Order.desc("createdAt")));
        Page<VeterinaryClinicVO> result = service.findAllByStatus(pageable);
    }



}