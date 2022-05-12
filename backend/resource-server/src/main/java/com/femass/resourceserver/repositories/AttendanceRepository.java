package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository  extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByProtocol( String protocol );
    List<Attendance> findByUserCall_Protocol( String callProtocol );
    List<Attendance> findByResponsible_Department_Name( String department );
    List<Attendance> findByResponsible_Account_Username( String responsibleUsername );
}