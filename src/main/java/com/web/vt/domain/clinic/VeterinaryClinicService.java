package com.web.vt.domain.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class VeterinaryClinicService {

    private final VeterinaryClinicRepository clinicRepository;

    public VeterinaryClinicVO save(VeterinaryClinicVO vo){
        VeterinaryClinic saved = clinicRepository.save(new VeterinaryClinic(vo));
        return new VeterinaryClinicVO(saved);
    }

    public VeterinaryClinicVO update(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new IllegalStateException("NOT EXIST ID");
        }

        // update method 고려
        VeterinaryClinic saved = find.get().name(vo.name())
                .contact(vo.contact())
                .remark(vo.remark());

        return new VeterinaryClinicVO(saved);
    }

    public VeterinaryClinicVO delete(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new IllegalStateException("NOT EXIST ID");
        }
        VeterinaryClinic saved = find.get().status("D");//deleted
        return new VeterinaryClinicVO().id(saved.id()).status(saved.status());
    }

    @Transactional(readOnly = true)
    public VeterinaryClinicVO findByIdAndStatus(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findByIdAndStatus(vo.id(), vo.status());
        return find.map(VeterinaryClinicVO::new).orElseGet(VeterinaryClinicVO::new);
    }

    @Transactional(readOnly = true)
    public Page<VeterinaryClinicVO> findAllByStatus(Pageable pageable){
        Page<VeterinaryClinic> find = clinicRepository.findAllByStatus("Y", pageable);
        return find.map(VeterinaryClinicVO::new);
    }




}
