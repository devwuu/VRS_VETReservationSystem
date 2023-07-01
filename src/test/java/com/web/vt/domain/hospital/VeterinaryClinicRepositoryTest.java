package com.web.vt.domain.hospital;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VeterinaryClinicRepositoryTest {

    @Autowired
    private VeterinaryClinicRepository repository;

    @Test @DisplayName("새로운 동물병원을 등록한다.")
    public void save() {
        VeterinaryClinic clinic = new VeterinaryClinic()
                .name("병아리 동물병원")
                .contact("0211112222")
                .remark("병아리 전문");

        VeterinaryClinic save = repository.save(clinic);

        assertThat(save.name()).isEqualTo(save.name());

    }

}