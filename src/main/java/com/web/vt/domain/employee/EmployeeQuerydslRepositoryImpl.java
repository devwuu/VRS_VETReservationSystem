package com.web.vt.domain.employee;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import com.web.vt.domain.common.dto.QEmployeeClinicDTO;
import com.web.vt.exceptions.NotFoundException;
import com.web.vt.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;

import static com.web.vt.domain.clinic.QVeterinaryClinic.veterinaryClinic;
import static com.web.vt.domain.employee.QEmployee.employee;

@RequiredArgsConstructor
public class EmployeeQuerydslRepositoryImpl implements EmployeeQuerydslRepository{

    private final JPAQueryFactory query;

    @Override
    public EmployeeClinicDTO findByEmployeeId(Long id) {

        EmployeeClinicDTO find = query.select(
                        new QEmployeeClinicDTO(
                                employee.id,
                                employee.loginId,
                                employee.password,
                                employee.role,
                                employee.position,
                                employee.status,
                                veterinaryClinic.id,
                                veterinaryClinic.name
                        )
                ).from(employee)
                .innerJoin(veterinaryClinic)
                .on(employee.clinic.id.eq(veterinaryClinic.id))
                .where(employee.id.eq(id))
                .fetchOne();

       if(ObjectUtil.isEmpty(find)){
           throw new NotFoundException("NOT EXIST EMPLOYEE");
       }

        return find;
    }


}
