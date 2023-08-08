package com.web.vt.domain.reservation;

import com.web.vt.domain.common.PageVO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationSearchCondition;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client/reservation")
public class ReservationClientController {

    private final ReservationService reservationService;

    @PostMapping("save")
    public ResponseEntity<ReservationVO> save(@RequestBody ReservationVO vo){

        if(ObjectUtil.isNotEmpty(vo.id())){
            throw new ValidationException("ID SHOULD BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.status())){
            throw new ValidationException("STATUS SHOULD NOT BE EMPTY");
        }
        if(ObjectUtil.isEmpty(vo.animalId())){
            throw new ValidationException("ANIMAL ID SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();

        ReservationVO saved = reservationService.save(vo.clinicId(clinicId));
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

    @GetMapping("all")
    public ResponseEntity<Page<ReservationAnimalGuardianDTO>> findAll(PageVO pageVO){

        if(pageVO.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        Pageable pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize(), by(desc("reservationDateTime")));

        Page<ReservationAnimalGuardianDTO> all = reservationService.findAllWithAnimalAndGuardian(
                clinicId,
                pageable
        );
        return ResponseEntity.ok().body(all);
    }

    // todo clinicId 에 security 적용
    @GetMapping("search")
    public ResponseEntity<Page<ReservationAnimalGuardianDTO>> searchAll(PageVO pageVO, ReservationSearchCondition condition){

        if(pageVO.getSize() == 0){
            throw new ValidationException("PAGINATION INFO SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        Pageable pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize(), by(desc("reservationDateTime")));

        Page<ReservationAnimalGuardianDTO> find = reservationService.searchAllWithAnimalAndGuardian(
                clinicId,
                condition,
                pageable
        );
        return ResponseEntity.ok().body(find);
    }

    @GetMapping("available")
    public ResponseEntity<List<ReservationSlotDTO>> findAllAvailableSlots(@RequestParam String reservationDateTime){

        if(StringUtil.isEmpty(reservationDateTime)){
            throw new ValidationException("RESERVATION DATE SHOULD NOT BE EMPTY");
        }

        Long clinicId = SecurityUtil.getEmployeePrincipal().getClinicId();
        Instant parsedReservationDateTime = LocalDateTime
                .parse(reservationDateTime, DateTimeFormatter.ISO_DATE_TIME)
                .toInstant(ZoneOffset.UTC);

        ReservationVO vo = new ReservationVO()
                .clinicId(clinicId)
                .reservationDateTime(parsedReservationDateTime);

        List<ReservationSlotDTO> slots = reservationService.findAllReservationSlots(vo);

        return ResponseEntity.ok().body(slots);
    }


}
