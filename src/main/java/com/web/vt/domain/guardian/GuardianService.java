package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class GuardianService {

    private final GuardianRepository guardianRepository;

    public GuardianVO save(GuardianVO vo){
        Guardian saved = guardianRepository.save(new Guardian(vo));
        return new GuardianVO(saved);
    }

    public GuardianVO update(GuardianVO vo){
        Optional<Guardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        Guardian saved = find.get().name(vo.name())
                .contact(vo.contact())
                .address(vo.address())
                .remark(vo.remark());

        return new GuardianVO(saved);
    }

    @Transactional(readOnly = true)
    public GuardianVO findById(GuardianVO vo){
        Optional<Guardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        return find.map(GuardianVO::new).get();
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
