package com.web.vt.domain.reservation;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.QReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.web.vt.domain.animal.QAnimal.animal;
import static com.web.vt.domain.guardian.QGuardian.guardian;
import static com.web.vt.domain.reservation.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationQuerydslRepositoryImpl implements ReservationQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public ReservationAnimalGuardianDTO findByIdWithAnimalAndGuardian(ReservationVO vo) {

        ReservationAnimalGuardianDTO find = query.select(new QReservationAnimalGuardianDTO(
                        reservation.id,
                        reservation.reservationDateTime,
                        reservation.status,
                        animal.id,
                        animal.name,
                        animal.species,
                        animal.age,
                        guardian.id,
                        guardian.name,
                        guardian.contact)
                ).from(reservation)
                .innerJoin(animal)
                .on(reservation.animal.id.eq(animal.id))
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(reservation.id.eq(vo.id())
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(guardian.status.eq(UsageStatus.USE)))
                .fetchOne();

        if(ObjectUtil.isEmpty(find)){
            throw new NotFoundException("NOT EXIST RESERVATION");
        }

        return find;
    }

    @Override
    public Page<ReservationAnimalGuardianDTO> findAllWithAnimalAndGuardian(Long clinicId, Pageable pageable) {

        List<ReservationAnimalGuardianDTO> content = query.select(new QReservationAnimalGuardianDTO(
                        reservation.id,
                        reservation.reservationDateTime,
                        reservation.status,
                        animal.id,
                        animal.name,
                        animal.species,
                        animal.age,
                        guardian.id,
                        guardian.name,
                        guardian.contact)
                ).from(reservation)
                .innerJoin(animal)
                .on(reservation.animal.id.eq(animal.id))
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(reservation.clinic.id.eq(clinicId)
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(guardian.status.eq(UsageStatus.USE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> countAllWithAnimalAndGuardian(clinicId).fetchOne());
    }

    public JPAQuery<Long> countAllWithAnimalAndGuardian(Long clinicId) {

        JPAQuery<Long> countQuery = query.select(reservation.count())
                .from(reservation)
                .innerJoin(animal)
                .on(reservation.animal.id.eq(animal.id))
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(reservation.clinic.id.eq(clinicId)
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(guardian.status.eq(UsageStatus.USE)));

        return countQuery;
    }
}
