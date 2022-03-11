package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.account.AgentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgentAccountRepository extends JpaRepository<AgentAccount, UUID> {

    Optional<AgentAccount> findByUsername( String username );

    boolean existsByUsername( String username );
}