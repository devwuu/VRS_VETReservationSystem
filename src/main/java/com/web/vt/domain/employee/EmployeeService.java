package com.web.vt.domain.employee;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeClinicDTO findById(EmployeeVO vo){
        return employeeRepository.findByEmployeeId(vo.id());
    }

}
