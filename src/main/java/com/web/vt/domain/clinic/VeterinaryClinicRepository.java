package com.web.vt.domain.clinic;

import com.web.vt.domain.common.enums.UsageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinaryClinicRepository extends JpaRepository<VeterinaryClinic, Long> {

    Optional<VeterinaryClinic> findByIdAndStatus(Long aLong, UsageStatus status);

    Page<VeterinaryClinic> findAllByStatus(UsageStatus status, Pageable pageable);
}
