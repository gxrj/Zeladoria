package com.femass.authserver.auth.repository;

import java.util.Optional;
import java.util.UUID;

import com.femass.authserver.auth.domain.users.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {

    Optional<Colaborador> findById( UUID id );

    @Query(""" 
        select c from Colaborador c
            where c.matricula = :matricula
    """ )
    Optional<Colaborador> findByUsername( @Param( "matricula" ) String username );
 }