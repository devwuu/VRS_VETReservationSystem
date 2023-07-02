package com.web.vt.domain.hospital;

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
@RequestMapping("/v1/rest/clinic")
public class VeterinaryClinicController {

    private final VeterinaryClinicService clinicService;

    @PostMapping("save")
    public ResponseEntity<VeterinaryClinicVO> save(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isNotEmpty(body.id())){
            throw new IllegalStateException("ALREADY EXIST ID");
        }
        VeterinaryClinicVO result = clinicService.save(body);
        return ResponseEntity.created(URI.create("/v1/rest/clinic")).body(result);
    }

}
