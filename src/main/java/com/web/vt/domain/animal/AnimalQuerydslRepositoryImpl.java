package com.web.vt.domain.animal;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.AnimalSearchCondition;
import com.web.vt.domain.common.dto.QAnimalGuardianDTO;
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

@RequiredArgsConstructor
public class AnimalQuerydslRepositoryImpl implements AnimalQuerydslRepository{

    private final JPAQueryFactory query;

    @Override
    public AnimalGuardianDTO findByIdWithGuardian(AnimalVO vo) {

        AnimalGuardianDTO find = query.select(new QAnimalGuardianDTO(
                        animal.id,
                        animal.name,
                        animal.species,
                        animal.age,
                        animal.remark,
                        animal.status,
                        guardian.id,
                        guardian.name,
                        guardian.contact,
                        guardian.address,
                        guardian.status,
                        guardian.remark
                ))
                .from(animal)
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(animal.id.eq(vo.id())
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(animal.guardian.status.eq(UsageStatus.USE))
                )
                .fetchOne();

        if(ObjectUtil.isEmpty(find)){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }

        return find;
    }

    @Override
    public Page<AnimalGuardianDTO> findAllWithGuardian(Long clinicId, Pageable pageable) {

        BooleanExpression defaultExpression = animal.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        List<AnimalGuardianDTO> contents = findPageableContents(pageable, defaultExpression);

        return PageableExecutionUtils.getPage(
                contents,
                pageable,
                () -> countAllWithGuardian(defaultExpression).fetchOne()
        );
    }


    @Override
    public Page<AnimalGuardianDTO> searchAllWithGuardian(Long clinicId, AnimalSearchCondition condition, Pageable pageable) {

        BooleanExpression defaultExpression = animal.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        BooleanExpression[] booleanExpressions = whereWith(defaultExpression, condition);

        List<AnimalGuardianDTO> contents = findPageableContents(pageable, booleanExpressions);

        return PageableExecutionUtils.getPage(
                contents,
                pageable,
                () -> countAllWithGuardian(booleanExpressions).fetchOne() );
    }

    private List<AnimalGuardianDTO> findPageableContents(Pageable pageable, BooleanExpression ...conditions) {
        List<AnimalGuardianDTO> contents = query.select(new QAnimalGuardianDTO(
                        animal.id,
                        animal.name,
                        animal.species,
                        animal.age,
                        animal.remark,
                        animal.status,
                        guardian.id,
                        guardian.name,
                        guardian.contact,
                        guardian.address,
                        guardian.status,
                        guardian.remark
                ))
                .from(animal)
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(conditions)
                .orderBy(orderByPageable(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return contents;
    }

    public JPAQuery<Long> countAllWithGuardian(BooleanExpression ...conditions) {

        JPAQuery<Long> countQuery = query.select(animal.count())
                .from(animal)
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(conditions);

        return countQuery;
    }

    private BooleanExpression[] whereWith(BooleanExpression defaultExpression, AnimalSearchCondition condition){
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        booleanExpressions.add(defaultExpression);

        if(StringUtil.isNotEmpty(condition.getAnimalName())){
            booleanExpressions.add(animal.name.like("%" + condition.getAnimalName() + "%"));
        }
        if(StringUtil.isNotEmpty(condition.getGuardianName())){
            booleanExpressions.add(guardian.name.like("%" + condition.getGuardianName() + "%"));
        }

        return booleanExpressions.toArray(BooleanExpression[]::new);
    }

    private OrderSpecifier<?>[] orderByPageable(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for(Sort.Order order : pageable.getSort()){
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            // 확장 가능성을 고려하여 switch문으로 남겨둠
            switch (order.getProperty()) {
                case "createdAt" ->
                        orderSpecifiers.add(new OrderSpecifier<Instant>(direction, animal.createdAt));
            }
        }
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }





}
