package com.web.vt.domain.animal;

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
@RequestMapping("/v1/animal")
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
        if(ObjectUtil.isEmpty(vo.clinicId())){
            throw new ValidationException("CLINIC ID SHOULD NOT BE EMPTY");
        }
        AnimalVO saved = animalService.save(vo);
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


}
