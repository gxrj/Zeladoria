package com.femass.authserver.auth.repository;

import java.util.Optional;
import java.util.UUID;

import com.femass.authserver.auth.domain.entities.Cidadao;
import com.femass.authserver.auth.domain.model.CidadaoCredentials;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadaoRepository extends JpaRepository<Cidadao, UUID> { 

    Optional<Cidadao> findByLogin( CidadaoCredentials login );
    Optional<Cidadao> findById( UUID id );
}