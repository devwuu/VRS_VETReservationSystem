package com.web.vt.domain.guardian;

import com.web.vt.domain.common.enums.UsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    Optional<Guardian> findByIdAndStatus(Long id, UsageStatus status);
}
