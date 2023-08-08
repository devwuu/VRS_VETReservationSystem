package com.web.vt.domain.reservationmanagement;

import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import com.web.vt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client/reservation-management")
public class ReservationManagementClientController {

    private final ReservationManagementService managementService;

    @GetMapping("info")
    public ResponseEntity<ReservationManagementVO> findByClinicId(){
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        ReservationManagementVO vo = new ReservationManagementVO().clinicId(clinicId);
        ReservationManagementVO find = managementService.findByClinicId(vo);
        return ResponseEntity.ok().body(find);
    }

    @PostMapping("update")
    public ResponseEntity<ReservationManagementVO> update(@RequestBody ReservationManagementVO vo){
        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        ReservationManagementVO updated = managementService.update(vo);
        return ResponseEntity.ok().body(updated);
    }



}
