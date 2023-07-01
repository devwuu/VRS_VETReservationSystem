package com.web.vt.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaryClinicRepository extends JpaRepository<VeterinaryClinic, Long> {
}
