package com.web.vt.domain.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class VeterinaryClinicService {

    private final VeterinaryClinicRepository clinicRepository;

    public VeterinaryClinicVO save(VeterinaryClinicVO vo){
        VeterinaryClinic saved = clinicRepository.save(new VeterinaryClinic(vo));
        return new VeterinaryClinicVO(saved);
    }


}
