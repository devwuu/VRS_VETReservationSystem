package com.web.vt.domain.clinic;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class VeterinaryClinicService {

    private final VeterinaryClinicRepository clinicRepository;

    public VeterinaryClinicVO save(VeterinaryClinicVO vo){
        VeterinaryClinic saved = clinicRepository.save(new VeterinaryClinic(vo));
        return new VeterinaryClinicVO(saved);
    }

    public VeterinaryClinicVO update(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ID");
        }

        // update method 고려
        VeterinaryClinic saved = find.get()
                .name(vo.name())
                .contact(vo.contact())
                .remark(vo.remark())
                .status(vo.status());

        return new VeterinaryClinicVO(saved);
    }

    public VeterinaryClinicVO delete(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ID");
        }
        VeterinaryClinic saved = find.get()
                .status(UsageStatus.DELETE)
                .name(null)
                .contact(null)
                .remark(null);
        return new VeterinaryClinicVO(saved);
    }

    @Transactional(readOnly = true)
    public Page<VeterinaryClinicVO> findAllByStatusIn(Pageable pageable, UsageStatus ...statuses){
        Page<VeterinaryClinic> find = clinicRepository.findAllByStatusIn(pageable, statuses);
        return find.map(VeterinaryClinicVO::new);
    }

    @Transactional(readOnly = true)
    public VeterinaryClinicVO findById(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ID");
        }
        return find.map(VeterinaryClinicVO::new).get();
    }

    // todo 제거 검토
    @Transactional(readOnly = true)
    public VeterinaryClinicVO findByIdAndStatus(VeterinaryClinicVO vo){
        Optional<VeterinaryClinic> find = clinicRepository.findByIdAndStatus(vo.id(), vo.status());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST CLINIC");
        }
        return find.map(VeterinaryClinicVO::new).get();
    }




}
