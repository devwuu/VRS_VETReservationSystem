package com.web.vt.domain.employee;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeQuerydslRepositoryImpl implements EmployeeQuerydslRepository{

    private final JPAQueryFactory query;

    @Override
    public EmployeeClinicDTO findByEmployeeId(String id) {
        return null;
    }


}
