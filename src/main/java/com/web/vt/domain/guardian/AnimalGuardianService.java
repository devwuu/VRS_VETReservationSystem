package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class AnimalGuardianService {

    private final AnimalGuardianRepository guardianRepository;

    public AnimalGuardianVO save(AnimalGuardianVO vo){
        AnimalGuardian saved = guardianRepository.save(new AnimalGuardian(vo));
        return new AnimalGuardianVO(saved);
    }

    public AnimalGuardianVO update(AnimalGuardianVO vo){
        Optional<AnimalGuardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        AnimalGuardian saved = find.get().name(vo.name())
                .contact(vo.contact())
                .address(vo.address())
                .remark(vo.remark());

        return new AnimalGuardianVO(saved);
    }

    @Transactional(readOnly = true)
    public AnimalGuardianVO findById(AnimalGuardianVO vo){
        Optional<AnimalGuardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        return find.map(AnimalGuardianVO::new).get();
    }

    public AnimalGuardianVO delete(AnimalGuardianVO vo){
        Optional<AnimalGuardian> find = guardianRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST GUARDIAN");
        }
        AnimalGuardian deleted = find.get()
                .status(UsageStatus.DELETE)
                .name(null)
                .contact(null)
                .address(null)
                .remark(null);

        return new AnimalGuardianVO(deleted);
    }

}
