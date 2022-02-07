package com.femass.resourceserver.repositories;

import java.util.Optional;
import java.util.UUID;

import com.femass.resourceserver.domain.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< UserEntity, UUID > {
    
    public Optional< UserEntity > findByUsername( String username );

    public boolean existsByUsername( String username );
}