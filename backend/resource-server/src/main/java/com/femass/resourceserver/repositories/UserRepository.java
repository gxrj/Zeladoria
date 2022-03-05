package com.femass.resourceserver.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< UserEntity, UUID > {
    
    Optional< UserEntity > findByUsername( String username );
    List< UserEntity > findAllByEnabled( Boolean isEnabled );

    boolean existsByUsername( String username );
}