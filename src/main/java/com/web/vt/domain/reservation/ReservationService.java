package com.web.vt.domain.reservation;

import com.web.vt.domain.animal.AnimalService;
import com.web.vt.domain.animal.AnimalVO;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.reservationmanagement.ReservationManagementService;
import com.web.vt.domain.reservationmanagement.ReservationManagementVO;
import com.web.vt.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationManagementService managementService;
    private final VeterinaryClinicService clinicService;
    private final AnimalService animalService;


    @Transactional(readOnly = true)
    public List<ReservationSlotDTO> findAllReservationSlots(ReservationVO vo){
        LocalDate criteriaDate = LocalDate.ofInstant(vo.reservationDateTime(), ZoneId.of("UTC"));

        Instant from = criteriaDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant to = criteriaDate.atTime(OffsetTime.MAX).toInstant();
        ReservationSearchCondition condition = new ReservationSearchCondition().from(from).to(to);

        // 기예약건
        List<ReservationSlotDTO> existSlots = reservationRepository.findAllByReservationTime(vo.clinicId(), condition);
        // 예약 관리 정보
        ReservationManagementVO managementInfo = managementService.findByClinicId(new ReservationManagementVO().clinicId(vo.clinicId()));

        // todo 고려사항
        //  예약 관리 정보가 front에 이미 있을 것이기 때문에 비즈니스 로직을 front로 이동시켜도 무방하지 않을까?

        LocalTime startTime = LocalTime.ofInstant(managementInfo.startDateTime(), ZoneOffset.UTC);
        LocalTime endTime = LocalTime.ofInstant(managementInfo.endDateTime(), ZoneOffset.UTC);
        LocalTime currentSlotTime = startTime;
        List<ReservationSlotDTO> allSlots = new ArrayList<>();

        while (!currentSlotTime.isAfter(endTime)){
            Instant parsedSlotTime = Instant.ofEpochSecond(currentSlotTime.toEpochSecond(criteriaDate, ZoneOffset.UTC));

            Optional<ReservationSlotDTO> existSlot = existSlots.stream()
                    .filter(s -> parsedSlotTime.equals(s.slotTime()))
                    .findAny();

            if(existSlot.isPresent()){
                allSlots.add(existSlot.get());
            }else{
                allSlots.add(new ReservationSlotDTO().slotTime(parsedSlotTime).available(true));
            }

            currentSlotTime = currentSlotTime.plusMinutes(30);
        }

        return allSlots;
    }


    @Transactional(readOnly = true)
    public ReservationAnimalGuardianDTO findByIdWithAnimalAndGuardian(ReservationVO vo){
        return reservationRepository.findByIdWithAnimalAndGuardian(vo);
    }

    @Transactional(readOnly = true)
    public Page<ReservationAnimalGuardianDTO> findAllWithAnimalAndGuardian(Long clinicId, Pageable pageable){
        return reservationRepository.findAllWithAnimalAndGuardian(clinicId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReservationAnimalGuardianDTO> searchAllWithAnimalAndGuardian(Long clinicId, ReservationSearchCondition condition, Pageable pageable){
        return reservationRepository.searchAllWithAnimalAndGuardian(clinicId, condition, pageable);
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
