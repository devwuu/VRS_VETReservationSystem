package com.web.vt.domain.animal;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.QAnimalGuardianDTO;
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
                .where(animal.clinic.id.eq(clinicId)
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(animal.guardian.status.eq(UsageStatus.USE))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(contents, pageable, () -> countAllWithGuardian(clinicId).fetchOne() );
    }

    @Override
    public JPAQuery<Long> countAllWithGuardian(Long clinicId) {

        JPAQuery<Long> countQuery = query.select(animal.count())
                .from(animal)
                .innerJoin(guardian)
                .on(animal.guardian.id.eq(guardian.id))
                .where(animal.clinic.id.eq(clinicId)
                        .and(animal.status.eq(UsageStatus.USE))
                        .and(animal.guardian.status.eq(UsageStatus.USE)));

        return countQuery;
    }


}
