package com.web.vt.domain.guardian;

import com.web.vt.domain.clinic.VeterinaryClinic;
import com.web.vt.domain.common.enums.UsageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long>, GuardianQuerydslRepository {
    Optional<Guardian> findByIdAndStatus(Long id, UsageStatus status);

    Page<Guardian> findAllByClinicAndStatus(VeterinaryClinic clinic, UsageStatus status, Pageable pageable);

}
