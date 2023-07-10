package com.web.vt.domain.reservation;

import com.web.vt.domain.animal.AnimalService;
import com.web.vt.domain.animal.AnimalVO;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final VeterinaryClinicService clinicService;
    private final AnimalService animalService;

    // todo 동물 정보와 보호자 정보 추가 필요
    @Transactional(readOnly = true)
    public ReservationVO findById(ReservationVO vo){

        Optional<Reservation> find = reservationRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST RESERVATION");
        }
        return find.map(ReservationVO::new).get();

    }

    public ReservationVO save(ReservationVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status(UsageStatus.USE);
        AnimalVO animal = new AnimalVO().id(vo.animalId()).status(UsageStatus.USE);

        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);
        AnimalVO findAnimal = animalService.findByIdAndStatus(animal);

        Reservation reservation = new Reservation(vo).addClinic(findClinic).addAnimal(findAnimal);
        Reservation saved = reservationRepository.save(reservation);

        return new ReservationVO(saved);

    }


}
