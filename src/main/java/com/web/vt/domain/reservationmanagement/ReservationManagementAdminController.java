package com.web.vt.domain.reservationmanagement;


import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/reservation-management")
public class ReservationManagementAdminController {

    private final ReservationManagementService managementService;

    @PostMapping("save")
    public ResponseEntity<ReservationManagementVO> save(@RequestBody ReservationManagementVO vo){
        if(ObjectUtil.isNotEmpty(vo.id())){
            throw new ValidationException("ID SHOULD BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.clinicId())){
            throw new ValidationException("CLINIC ID SHOULD NOT BE EMPTY");
        }
        ReservationManagementVO saved = managementService.save(vo);
        return ResponseEntity.created(URI.create("/v1/reservation-management/"+vo.clinicId())).body(saved);
    }

}
