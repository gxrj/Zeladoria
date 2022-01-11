package com.femass.authserver.auth.repository;

import java.util.Optional;
import java.util.UUID;

import com.femass.authserver.auth.domain.entities.Colaborador;
import com.femass.authserver.auth.domain.model.ColaboradorCredentials;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {
    
    Optional<Colaborador> findById( UUID id );
    Optional<Colaborador> findByLogin( ColaboradorCredentials login );
 }