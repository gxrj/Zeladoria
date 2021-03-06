package com.femass.authzserver.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.authzserver.auth.models.domain.AgentAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository< AgentAccount, UUID > {
    
    Optional<AgentAccount> findByUsername( String username );
    boolean existsByUsername( String username );
}