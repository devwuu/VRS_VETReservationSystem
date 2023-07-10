package com.web.vt.domain.animal;

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
import org.springframework.data.support.PageableExecutionUtils;

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

        BooleanExpression conditions = animal.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        List<AnimalGuardianDTO> contents = findPageableContents(pageable, conditions);

        return PageableExecutionUtils.getPage(contents, pageable, () -> countAllWithGuardian(clinicId, conditions).fetchOne() );
    }


    @Override
    public Page<AnimalGuardianDTO> searchAllWithGuardian(Long clinicId, AnimalSearchCondition condition, Pageable pageable) {

        BooleanExpression conditions = animal.clinic.id.eq(clinicId)
                .and(animal.status.eq(UsageStatus.USE))
                .and(guardian.status.eq(UsageStatus.USE));

        List<AnimalGuardianDTO> contents = findPageableContents(pageable, conditions, animalNameLike(condition.animalName()), guardianNameLike(condition.guardianName()));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countAllWithGuardian(clinicId, conditions, animalNameLike(condition.animalName()), guardianNameLike(condition.guardianName())).fetchOne() );
    }

    public JPAQuery<Long> countAllWithGuardian(Long clinicId, BooleanExpression ...conditions) {

        JPAQuery<Long> countQuery = query.select(animal.count())
                .from(animal)
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(conditions);

        return countQuery;
    }

    private BooleanExpression animalNameLike(String animalName) {
        if(StringUtil.isNotEmpty(animalName)){
            return animal.name.like("%"+ animalName +"%");
        }else{
            return null;
        }
    }

    private BooleanExpression guardianNameLike(String guardianName) {
        if(StringUtil.isNotEmpty(guardianName)){
            return guardian.name.like("%"+ guardianName +"%");
        }else{
            return null;
        }
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return contents;
    }





}
