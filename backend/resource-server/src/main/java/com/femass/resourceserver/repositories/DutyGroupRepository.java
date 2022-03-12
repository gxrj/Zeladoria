package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.DutyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DutyGroupRepository extends JpaRepository<DutyGroup, UUID> {
    Optional<DutyGroup> findByName( String name );
}