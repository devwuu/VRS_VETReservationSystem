package com.web.vt.domain.reservationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationManagementRepository extends JpaRepository<ReservationManagement, Long> {

    Optional<ReservationManagement> findByClinicId(Long clinicId);

}
