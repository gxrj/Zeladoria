package com.femass.authzserver.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.authzserver.auth.models.domain.CitizenAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenAccount, UUID> {
    
    Optional<CitizenAccount> findByUsername( String username );
    boolean existsByUsername( String username );
}