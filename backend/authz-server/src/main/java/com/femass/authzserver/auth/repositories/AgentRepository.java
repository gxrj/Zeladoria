package com.femass.authzserver.auth.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.authzserver.auth.models.domain.AgentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository< AgentEntity, UUID > {
    
    public Optional< AgentEntity > findByUsername( String username );
}