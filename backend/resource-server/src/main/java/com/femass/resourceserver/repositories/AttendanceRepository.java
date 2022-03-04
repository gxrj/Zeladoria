package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttendanceRepository  extends JpaRepository<Attendance, UUID> {
}