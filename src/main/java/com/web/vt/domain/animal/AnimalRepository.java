package com.web.vt.domain.animal;

import com.web.vt.domain.common.enums.UsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByIdAndStatus(Long id, UsageStatus status);

}
