package com.femass.resourceserver.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.user.AgentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository< AgentEntity, UUID > {
    
    Optional< AgentEntity > findByUsername( String username );

    boolean existsByAgentCredentials_Username( String username );
}