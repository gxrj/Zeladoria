package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.account.CitizenAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CitizenAccountRepository extends JpaRepository<CitizenAccount, UUID> {

    Optional<CitizenAccount> findByUsername( String username );
    boolean existsByUsername( String username );
}