package com.femass.resourceserver.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.Agent;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {

    Optional<Agent> findByAccount_Username( String username );
    Optional<Agent> findByAccount_Credentials( String cpfCredential );

    boolean existsByAccount_Username( String username );
}