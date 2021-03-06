package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {

    Optional<Duty> findByDescription( String description );
    List<Duty> findByDepartment_Name( String departmentName );
    List<Duty> findByDutyGroup_Name( String categoryName );
}