package com.web.vt.domain.guardian;

import com.web.vt.domain.common.PageVO;
import com.web.vt.domain.common.dto.GuardianSearchCondition;
import com.web.vt.domain.common.enums.UsageStatus;
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
        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        GuardianVO saved = guardianService.save(vo.clinicId(clinicId));
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

    @GetMapping("all")
    public ResponseEntity<Page<GuardianVO>> findAll(PageVO pageVO){

        if(pageVO.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        GuardianVO vo = new GuardianVO().clinicId(clinicId).status(UsageStatus.USE);
        Pageable pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize());

        Page<GuardianVO> all = guardianService.findAllByClinicAndStatus(vo, pageable);

        return ResponseEntity.ok().body(all);
    }

    @GetMapping("search")
    public ResponseEntity<Page<GuardianVO>> searchAll(PageVO pageVO, GuardianSearchCondition condition){

        if(pageVO.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        condition.setClinicId(clinicId);
        Pageable pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize());

        Page<GuardianVO> find = guardianService.searchAll(condition, pageable);

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
