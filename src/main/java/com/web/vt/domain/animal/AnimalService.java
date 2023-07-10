package com.web.vt.domain.animal;

import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.guardian.GuardianService;
import com.web.vt.domain.guardian.GuardianVO;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final VeterinaryClinicService clinicService;
    private final GuardianService guardianService;

    public AnimalVO save(AnimalVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status(UsageStatus.USE);
        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);
        Animal animal = new Animal(vo).addClinic(findClinic);

        GuardianVO findGuardian = null;
        if(ObjectUtil.isNotEmpty(vo.guardian())){
            findGuardian = guardianService.findByIdAndStatus(vo.guardian());
            animal.addGuardian(findGuardian);
        }

        Animal persist = animalRepository.save(animal);

        AnimalVO saved = new AnimalVO(persist).clinicId(persist.clinic().id());

        if(ObjectUtil.isNotEmpty(findGuardian)){
            saved.guardian(findGuardian);
        }

        return saved;
    }

    public AnimalVO update(AnimalVO vo){

        Optional<Animal> find = animalRepository.findById(vo.id());

        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }

        Animal persist = find.get().name(vo.name())
                .species(vo.species())
                .age(vo.age())
                .remark(vo.remark());

        AnimalVO saved = new AnimalVO(persist).clinicId(vo.clinicId());

        if(ObjectUtil.isNotEmpty(vo.guardian())){
            persist.addGuardian(vo.guardian());
            saved.guardian(vo.guardian());
        }

        return saved;
    }

    public AnimalVO delete(AnimalVO vo){
        Optional<Animal> find = animalRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }
        Animal deleted = find.get().status(UsageStatus.DELETE)
                .name(null)
                .age(0L)
                .species(null)
                .remark(null);

        return new AnimalVO(deleted);
    }

    @Transactional(readOnly = true)
    public AnimalVO findByIdAndStatus(AnimalVO vo){
        Optional<Animal> find = animalRepository.findByIdAndStatus(vo.id(), vo.status());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }
        return find.map(AnimalVO::new).get();
    }

    @Transactional(readOnly = true)
    public AnimalGuardianDTO findByIdWithGuardian(AnimalVO vo){
        AnimalGuardianDTO find = animalRepository.findByIdWithGuardian(vo);
        return find;
    }

    @Transactional(readOnly = true)
    public Page<AnimalGuardianDTO> findAllWithGuardian(Long clinicId, Pageable pageable){
        Page<AnimalGuardianDTO> all = animalRepository.findAllWithGuardian(clinicId, pageable);
        return all;
    }


}
