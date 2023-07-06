package com.web.vt.domain.reservationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationManagementRepository extends JpaRepository<ReservationManagement, Long> {

}
