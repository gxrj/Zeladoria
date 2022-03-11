package com.femass.resourceserver.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.Citizen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, UUID> {
    
    Optional<Citizen> findByAccount_Username( String username );
    List<Citizen> findAllByAccount_Enabled( Boolean isEnabled );

    boolean existsByAccount_Username( String username );
}