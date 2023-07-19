package com.web.vt.domain.reservation;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.QReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationAnimalGuardianDTO;
import com.web.vt.domain.common.dto.ReservationSearchCondition;
import com.web.vt.domain.common.enums.ReservationStatus;
import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.Instant;
import java.util.ArrayList;
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

        BooleanExpression conditions = reservation.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        List<ReservationAnimalGuardianDTO> content = findPageableContents(pageable, conditions);

        return PageableExecutionUtils.getPage(content, pageable, () -> countAllWithAnimalAndGuardian(conditions).fetchOne());
    }

    @Override
    public Page<ReservationAnimalGuardianDTO> searchAllWithAnimalAndGuardian(Long clinicId, ReservationSearchCondition condition, Pageable pageable) {

        BooleanExpression defaultExpression = reservation.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        BooleanExpression[] booleanExpressions = whereWith(defaultExpression, condition);

        List<ReservationAnimalGuardianDTO> content = findPageableContents(pageable, booleanExpressions);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> countAllWithAnimalAndGuardian(booleanExpressions).fetchOne()
        );
    }


    @Override
    public List<ReservationSlotDTO> findAllByReservationTime(Long clinicId, ReservationSearchCondition condition) {

        BooleanExpression defaultExpression = reservation.clinic.id.eq(clinicId)
                .and(reservation.status.eq(ReservationStatus.APPROVED));

        List<Instant> reserved = query.select(reservation.reservationDateTime)
                .from(reservation)
                .where(whereWith(defaultExpression, condition))
                .fetch();

        List<ReservationSlotDTO> slots = reserved.stream()
                .map(t -> new ReservationSlotDTO().slotTime(t).available(false))
                .toList();

        return slots;
    }

    private List<ReservationAnimalGuardianDTO> findPageableContents(Pageable pageable, BooleanExpression... booleanExpressions) {

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
                .where(booleanExpressions)
                .orderBy(orderByPageable(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    private JPAQuery<Long> countAllWithAnimalAndGuardian(BooleanExpression ...booleanExpressions) {

        JPAQuery<Long> countQuery = query.select(reservation.count())
                .from(reservation)
                .innerJoin(animal)
                .on(reservation.animal.id.eq(animal.id))
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(booleanExpressions);

        return countQuery;
    }

    private BooleanExpression[] whereWith(BooleanExpression defaultExpression, ReservationSearchCondition condition){
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        booleanExpressions.add(defaultExpression);

        if(StringUtil.isNotEmpty(condition.getAnimalName())){
            booleanExpressions.add(animal.name.like("%" + condition.getAnimalName() + "%"));
        }
        if(StringUtil.isNotEmpty(condition.getGuardianName())){
            booleanExpressions.add(guardian.name.like("%" + condition.getGuardianName() + "%"));
        }
        if(ObjectUtil.isNotEmpty(condition.getFrom()) && ObjectUtil.isNotEmpty(condition.getTo())){
            booleanExpressions.add(reservation.reservationDateTime.between(condition.getFrom(), condition.getTo()));
        }

        return booleanExpressions.toArray(BooleanExpression[]::new);
    }

    private OrderSpecifier<?>[] orderByPageable(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for(Sort.Order order : pageable.getSort()){
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            switch (order.getProperty()) {
                case "reservationDateTime" ->
                        orderSpecifiers.add(new OrderSpecifier<Instant>(direction, reservation.reservationDateTime));
                case "createdAt" ->
                        orderSpecifiers.add(new OrderSpecifier<Instant>(direction, reservation.createdAt));
            }
        }
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }


}
