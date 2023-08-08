package com.web.vt.domain.clinic;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import com.web.vt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client/clinic")
public class VeterinaryClinicClientController {

    private final VeterinaryClinicService clinicService;

    @GetMapping("/info")
    public ResponseEntity<VeterinaryClinicVO> findByIdAndStatus(){
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        VeterinaryClinicVO find = new VeterinaryClinicVO().id(clinicId).status(UsageStatus.USE);
        VeterinaryClinicVO result = clinicService.findByIdAndStatus(find);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("update")
    public ResponseEntity<VeterinaryClinicVO> update(@RequestBody VeterinaryClinicVO body){
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        if(ObjectUtil.isEmpty(body.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        VeterinaryClinicVO update = clinicService.update(body.id(clinicId));
        return ResponseEntity.ok().body(update);
    }

}
