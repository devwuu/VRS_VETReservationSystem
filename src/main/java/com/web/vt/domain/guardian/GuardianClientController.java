package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client/guardian")
public class GuardianClientController {

    private final GuardianService guardianService;

    @PostMapping("save")
    public ResponseEntity<GuardianVO> save(@RequestBody GuardianVO vo){
        if(ObjectUtil.isNotEmpty(vo.id())){
            throw new ValidationException("ID SHOULD BE EMPTY");
        }
        GuardianVO saved = guardianService.save(vo);
        return ResponseEntity.created(URI.create("/v1/guardian/"+saved.id())).body(saved);
    }

    @PostMapping("update")
    public ResponseEntity<GuardianVO> update(@RequestBody GuardianVO vo){
        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        GuardianVO saved = guardianService.update(vo);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<GuardianVO> find(@PathVariable String id){
        if(StringUtil.isEmpty(id)){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        GuardianVO vo = new GuardianVO().id(Long.parseLong(id)).status(UsageStatus.USE);
        GuardianVO find = guardianService.findByIdAndStatus(vo);
        return ResponseEntity.ok().body(find);
    }

    @PostMapping("delete")
    public ResponseEntity<GuardianVO> delete(@RequestBody GuardianVO vo){
        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        GuardianVO deleted = guardianService.delete(vo);
        return ResponseEntity.ok().body(deleted);
    }

}
