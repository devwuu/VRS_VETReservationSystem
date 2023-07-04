package com.web.vt.domain.reservation;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.clinic.VeterinaryClinicService;
import com.web.vt.domain.clinic.VeterinaryClinicVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationManagementService {

    private final ReservationManagementRepository reservationManagementRepository;
    private final VeterinaryClinicService clinicService;

    public ReservationManagementVO save(ReservationManagementVO vo){

        VeterinaryClinicVO clinic = new VeterinaryClinicVO().id(vo.clinicId()).status("Y");
        VeterinaryClinicVO findClinic = clinicService.findByIdAndStatus(clinic);

        ReservationManagement management = new ReservationManagement(vo)
                .addClinic(new VeterinaryClinic(findClinic));

        ReservationManagement saved = reservationManagementRepository.save(management);

        return new ReservationManagementVO(saved).addClinic(saved.clinic());
    }


}
