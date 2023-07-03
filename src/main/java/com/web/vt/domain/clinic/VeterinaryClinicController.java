package com.web.vt.domain.clinic;

import com.web.vt.domain.common.PageVO;
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
@RequestMapping("/v1/rest/clinic")
public class VeterinaryClinicController {

    private final VeterinaryClinicService clinicService;

    @PostMapping("save")
    public ResponseEntity<VeterinaryClinicVO> save(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isNotEmpty(body.id())){
            throw new IllegalStateException("ID SHOULD BE NULL");
        }
        VeterinaryClinicVO result = clinicService.save(body);
        return ResponseEntity.created(URI.create("/v1/rest/clinic")).body(result);
    }

    @GetMapping
    public ResponseEntity<VeterinaryClinicVO> findByIdAnsStatus(@RequestParam String id){
        VeterinaryClinicVO find = new VeterinaryClinicVO().id(Long.parseLong(id)).status("Y");
        VeterinaryClinicVO result = clinicService.findByIdAndStatus(find);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("update")
    public ResponseEntity<VeterinaryClinicVO> update(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isEmpty(body.id())){
            throw new IllegalStateException("ID SHOULD NOT BE NULL");
        }
        VeterinaryClinicVO update = clinicService.update(body);
        return ResponseEntity.ok().body(update);
    }

    @PostMapping("delete")
    public ResponseEntity<VeterinaryClinicVO> delete(@RequestBody VeterinaryClinicVO body){
        if(ObjectUtil.isEmpty(body.id())){
            throw new IllegalStateException("ID SHOULD NOT BE NULL");
        }
        VeterinaryClinicVO result = clinicService.delete(body);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("all")
    public ResponseEntity<Page<VeterinaryClinicVO>> findAll(PageVO vo){
        if(vo.getPage() == 0 || vo.getSize() == 0){
            throw new IllegalStateException("PAGINATION INFO IS EMPTY");
        }
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), by(desc("createdAt")));
        Page<VeterinaryClinicVO> result = clinicService.findAllByStatus(pageable);
        return ResponseEntity.ok().body(result);
    }

}
