package com.web.vt.domain.animal;

import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.guardian.AnimalGuardianService;
import com.web.vt.domain.guardian.AnimalGuardianVO;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final VeterinaryClinicService clinicService;
    private final AnimalGuardianService guardianService;

    public AnimalVO save(AnimalVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status("Y");
        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);
        Animal animal = new Animal(vo).addClinic(findClinic);

        AnimalGuardianVO findGuardian = null;
        if(ObjectUtil.isNotEmpty(vo.guardian())){
            findGuardian = guardianService.findById(vo.guardian());
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

}
