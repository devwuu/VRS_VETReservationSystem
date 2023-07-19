package com.web.vt.domain.reservation;

import com.web.vt.domain.common.PageVO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.exceptions.ValidationException;
import com.web.vt.utils.ObjectUtil;
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
@RequestMapping("/v1/reservation")
public class ReservationClientController {

    private final ReservationService reservationService;

    // todo clinicId 에 security 적용
    @PostMapping("save")
    public ResponseEntity<ReservationVO> save(@RequestBody ReservationVO vo){

        if(ObjectUtil.isNotEmpty(vo.id())){
            throw new ValidationException("ID SHOULD BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.clinicId())){
            throw new ValidationException("CLINIC ID SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.animalId())){
            throw new ValidationException("ANIMAL ID SHOULD NOT BE EMPTY");
        }
        ReservationVO saved = reservationService.save(vo);
        return ResponseEntity.created(URI.create("/v1/reservation/"+saved.id())).body(saved);
    }

    @PostMapping("update")
    public ResponseEntity<ReservationVO> update(@RequestBody ReservationVO vo){
        if(ObjectUtil.isEmpty(vo.id())){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        ReservationVO updated = reservationService.update(vo);
        return ResponseEntity.ok().body(updated);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationAnimalGuardianDTO> findById(@PathVariable String id){
        if(StringUtil.isEmpty(id)){
            throw new ValidationException("ID SHOULD NOT BE EMPTY");
        }
        ReservationAnimalGuardianDTO find = reservationService.findByIdWithAnimalAndGuardian(
                new ReservationVO().id(Long.parseLong(id))
        );
        return ResponseEntity.ok().body(find);
    }

    // todo clinicId 에 security 적용
    @GetMapping("all")
    public ResponseEntity<Page<ReservationAnimalGuardianDTO>> findAll(@RequestParam String clinicId, PageVO pageVO){
        if(StringUtil.isEmpty(clinicId)){
            throw new ValidationException("CLINIC ID SHOULD NOT BE EMPTY");
        }
        if(pageVO.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }
        Pageable pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize(), by(desc("reservationDateTime")));
        Page<ReservationAnimalGuardianDTO> all = reservationService.findAllWithAnimalAndGuardian(
                Long.parseLong(clinicId),
                pageable
        );
        return ResponseEntity.ok().body(all);
    }


}
