package com.web.vt.domain.employee;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;

public interface EmployeeQuerydslRepository {

   EmployeeClinicDTO findByEmployeeId(Long id);

}
