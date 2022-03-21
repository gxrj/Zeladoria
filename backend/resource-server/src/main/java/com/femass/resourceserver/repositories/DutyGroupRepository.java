package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.DutyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DutyGroupRepository extends JpaRepository<DutyGroup, Long> {
    Optional<DutyGroup> findByName( String name );
}