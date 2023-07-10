package com.web.vt.domain.reservation;

import com.web.vt.domain.animal.AnimalService;
import com.web.vt.domain.animal.AnimalVO;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
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
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final VeterinaryClinicService clinicService;
    private final AnimalService animalService;

    @Transactional(readOnly = true)
    public ReservationAnimalGuardianDTO findByIdWithAnimalAndGuardian(ReservationVO vo){
        return reservationRepository.findByIdWithAnimalAndGuardian(vo);
    }

    @Transactional(readOnly = true)
    public Page<ReservationAnimalGuardianDTO> findAllWithAnimalAndGuardian(Long clinicId, Pageable pageable){
        return reservationRepository.findAllWithAnimalAndGuardian(clinicId, pageable);
    }

    // todo save하기 위한 정보를 조회할 수 있는 api가 필요
    public ReservationVO save(ReservationVO vo){
        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status(UsageStatus.USE);
        AnimalVO animal = new AnimalVO().id(vo.animalId()).status(UsageStatus.USE);

        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);
        AnimalVO findAnimal = animalService.findByIdAndStatus(animal);

        Reservation reservation = new Reservation(vo).addClinic(findClinic).addAnimal(findAnimal);
        Reservation saved = reservationRepository.save(reservation);

        return new ReservationVO(saved);

    }

    public ReservationVO update(ReservationVO vo){
        Optional<Reservation> find = reservationRepository.findById(vo.id());
        if(find.isEmpty()){
            throw new NotFoundException("NOT EXIST RESERVATION");
        }
        Reservation persist = find.get().reservationDateTime(vo.reservationDateTime())
                .status(vo.status())
                .remark(vo.remark());
        return new ReservationVO(persist);
    }


}
