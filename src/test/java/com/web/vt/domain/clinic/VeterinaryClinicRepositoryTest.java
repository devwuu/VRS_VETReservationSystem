package com.web.vt.domain.clinic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VeterinaryClinicRepositoryTest {

    @Autowired
    private VeterinaryClinicRepository repository;

    @Test @DisplayName("새로운 동물병원을 등록한다.")
    public void save() {
        VeterinaryClinic clinic = new VeterinaryClinic()
                .name("TEST")
                .contact("0211112222")
                .status("Y")
                .remark("병아리 전문");

        VeterinaryClinic save = repository.save(clinic);

        assertThat(save.name()).isEqualTo(save.name());

    }

}