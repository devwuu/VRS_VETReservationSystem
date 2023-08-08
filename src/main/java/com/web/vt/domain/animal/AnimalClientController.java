package com.web.vt.domain.animal;

import com.web.vt.domain.common.PageVO;
import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.AnimalSearchCondition;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import com.web.vt.utils.SecurityUtil;
import com.web.vt.utils.StringUtil;
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
@RequestMapping("/v1/client/animal")
public class AnimalClientController {

    private final AnimalService animalService;

    @PostMapping("save")
    public ResponseEntity<AnimalVO> save(@RequestBody AnimalVO vo){
        if(ObjectUtil.isNotEmpty(vo.id())){
            throw new ValidationException("ID SHOULD BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        AnimalVO saved = animalService.save(vo.clinicId(clinicId));
        return ResponseEntity.created(URI.create("/v1/animal/"+saved.id())).body(saved);
    }

    @PostMapping("update")
    public ResponseEntity<AnimalVO> update(@RequestBody AnimalVO vo){

        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        AnimalVO saved = animalService.update(vo);
        return ResponseEntity.ok().body(saved);
    }

    @PostMapping("delete")
    public ResponseEntity<AnimalVO> delete(@RequestBody AnimalVO vo){
        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        AnimalVO delete = animalService.delete(vo);
        return ResponseEntity.ok().body(delete);
    }

    @GetMapping("all")
    public ResponseEntity<Page<AnimalGuardianDTO>> findAll(PageVO vo){
        if(vo.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), by(desc("createdAt")));
        Page<AnimalGuardianDTO> result = animalService.findAllWithGuardian(clinicId, pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimalGuardianDTO> findById(@PathVariable String id){
        if(StringUtil.isEmpty(id)){
            throw new ValidationException("ANIMAL ID SHOULD NOT BE EMPTY");
        }
        AnimalGuardianDTO find = animalService.findByIdWithGuardian(
                new AnimalVO().id(Long.parseLong(id))
        );
        return ResponseEntity.ok().body(find);
    }

    @GetMapping("search")
    public ResponseEntity<Page<AnimalGuardianDTO>> searchAll(AnimalSearchCondition condition, PageVO vo) {
        if(vo.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), by(desc("createdAt")));
        Page<AnimalGuardianDTO> search = animalService.searchAllWithGuardian(
                clinicId,
                condition,
                pageable
        );
        return ResponseEntity.ok().body(search);
    }


}
