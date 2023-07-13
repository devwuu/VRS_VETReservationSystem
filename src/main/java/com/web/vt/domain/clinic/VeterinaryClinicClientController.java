package com.web.vt.domain.clinic;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clinic")
public class VeterinaryClinicClientController {

    private final VeterinaryClinicService clinicService;

    @GetMapping("{id}")
    public ResponseEntity<VeterinaryClinicVO> findByIdAndStatus(@PathVariable String id){
        VeterinaryClinicVO find = new VeterinaryClinicVO().id(Long.parseLong(id)).status(UsageStatus.USE);
        VeterinaryClinicVO result = clinicService.findByIdAndStatus(find);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("update")
    public ResponseEntity<VeterinaryClinicVO> update(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isEmpty(body.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        VeterinaryClinicVO update = clinicService.update(body);
        return ResponseEntity.ok().body(update);
    }

}
