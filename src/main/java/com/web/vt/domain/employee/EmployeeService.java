package com.web.vt.domain.employee;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public EmployeeClinicDTO findById(Long id){
        return employeeRepository.findByEmployeeId(id);
    }

}
