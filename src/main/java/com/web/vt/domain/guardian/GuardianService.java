package com.web.vt.domain.guardian;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.dto.GuardianSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class GuardianService {

    private final GuardianRepository guardianRepository;
    private final VeterinaryClinicService clinicService;

    public GuardianVO save(GuardianVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status(UsageStatus.USE);
        VeterinaryClinicVO find = clinicService.findByIdAndStatus(clinic);
        Guardian saved = guardianRepository.save(new Guardian(vo).addClinic(find));
        return new GuardianVO(saved).clinicId(find.id());
    }

    public GuardianVO update(GuardianVO vo){
        Optional<Guardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        Guardian saved = find.get().name(vo.name())
                .contact(vo.contact())
                .address(vo.address())
                .remark(vo.remark())
                .status(vo.status());

        return new GuardianVO(saved);
    }

    @Transactional(readOnly = true)
    public GuardianVO findByIdAndStatus(GuardianVO vo){
        Optional<Guardian> find = guardianRepository.findByIdAndStatus(vo.id(), vo.status());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        return find.map(GuardianVO::new).get();
    }

    @Transactional(readOnly = true)
    public Page<GuardianVO> findAllByClinicAndStatus(GuardianVO vo, Pageable pageable){
        Page<Guardian> all = guardianRepository.findAllByClinicAndStatus(
                new VeterinaryClinic().id(vo.clinicId()),
                vo.status(),
                pageable
        );
        return all.map(guardian -> new GuardianVO(guardian).clinicId(vo.clinicId()));
    }

    @Transactional(readOnly = true)
    public Page<GuardianVO> searchAll(GuardianSearchCondition condition, Pageable pageable){
        Page<GuardianVO> all = guardianRepository.searchAll(
                condition,
                pageable
        );
        return all;
    }

    public GuardianVO delete(GuardianVO vo){
        Optional<Guardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        Guardian deleted = find.get()
                .status(UsageStatus.DELETE)
                .name(null)
                .contact(null)
                .address(null)
                .remark(null);

        return new GuardianVO(deleted);
    }

}
