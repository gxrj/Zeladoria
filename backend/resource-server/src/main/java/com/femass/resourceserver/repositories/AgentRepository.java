package com.femass.resourceserver.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.Agent;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {

    Optional<Agent> findByAccount_Username( String username );
    Optional<Agent> findByAccount_Credentials_Cpf( String cpfCredential );

    List<Agent> findByDepartment_Name( String deptName );

    boolean existsByAccount_Username( String username );
}