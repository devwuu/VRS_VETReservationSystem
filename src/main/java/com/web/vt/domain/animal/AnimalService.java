package com.web.vt.domain.animal;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.exceptions.NotFoundException;
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

    public AnimalVO save(AnimalVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status("Y");
        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);
        Animal animal = new Animal(vo).clinic(new VeterinaryClinic(findClinic));
        Animal saved = animalRepository.save(animal);
        return new AnimalVO(saved).clinicId(saved.clinic().id());
    }

    public AnimalVO update(AnimalVO vo){
        Optional<Animal> find = animalRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }
        Animal updated = find.get().name(vo.name())
                .species(vo.species())
                .age(vo.age())
                .remark(vo.remark());

        return new AnimalVO(updated).clinicId(vo.clinicId());
    }

}
