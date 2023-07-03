package com.web.vt.domain.clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinaryClinicRepository extends JpaRepository<VeterinaryClinic, Long> {

    Optional<VeterinaryClinic> findByIdAndStatus(Long aLong, String status);

    Page<VeterinaryClinic> findAllByStatus(String status, Pageable pageable);
}
