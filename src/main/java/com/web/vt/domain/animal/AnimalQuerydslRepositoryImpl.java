package com.web.vt.domain.animal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.AnimalGuardianDTO;
import com.web.vt.domain.common.dto.QAnimalGuardianDTO;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;

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
                .where(animal.id.eq(vo.id()))
                .fetchOne();

        if(ObjectUtil.isEmpty(find)){
            throw new NotFoundException("NOT EXIST ANIMAL");
        }

        return find;
    }


}
