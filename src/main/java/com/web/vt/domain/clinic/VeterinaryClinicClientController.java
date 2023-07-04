package com.web.vt.domain.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clinic")
public class VeterinaryClinicClientController {

    private final VeterinaryClinicService clinicService;

    @GetMapping
    public ResponseEntity<VeterinaryClinicVO> findByIdAndStatus(@RequestParam String id){
        VeterinaryClinicVO find = new VeterinaryClinicVO().id(Long.parseLong(id)).status("Y");
        VeterinaryClinicVO result = clinicService.findByIdAndStatus(find);
        return ResponseEntity.ok().body(result);
    }

}
