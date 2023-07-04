package com.web.vt.domain.clinic;

import com.web.vt.domain.common.PageVO;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/clinic")
public class VeterinaryClinicAdminController {

    private final VeterinaryClinicService clinicService;

    /**
     * for admin
     * */
    @PostMapping("save")
    public ResponseEntity<VeterinaryClinicVO> save(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isNotEmpty(body.id())){
            throw new ValidationException("ID SHOULD BE NULL");
        }
        VeterinaryClinicVO result = clinicService.save(body);
        return ResponseEntity.created(URI.create("/v1/rest/clinic")).body(result);
    }

    @PostMapping("update")
    public ResponseEntity<VeterinaryClinicVO> update(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isEmpty(body.id())){
            throw new ValidationException("ID SHOULD NOT BE NULL");
        }
        VeterinaryClinicVO update = clinicService.update(body);
        return ResponseEntity.ok().body(update);
    }

    @PostMapping("delete")
    public ResponseEntity<VeterinaryClinicVO> delete(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isEmpty(body.id())){
            throw new ValidationException("ID SHOULD NOT BE NULL");
        }
        VeterinaryClinicVO result = clinicService.delete(body);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("all")
    public ResponseEntity<Page<VeterinaryClinicVO>> findAll(PageVO vo){
        if(vo.getSize() == 0){
            throw new ValidationException("PAGINATION INFO IS EMPTY");
        }
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), by(desc("createdAt")));
        Page<VeterinaryClinicVO> result = clinicService.findAll(pageable);
        return ResponseEntity.ok().body(result);
    }

}
