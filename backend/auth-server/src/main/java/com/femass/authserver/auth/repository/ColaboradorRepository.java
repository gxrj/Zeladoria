package com.femass.authserver.auth.repository;

import java.util.Optional;
import java.util.UUID;

import com.femass.authserver.auth.domain.entities.Colaborador;
import com.femass.authserver.auth.domain.model.ColaboradorUsername;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {

    Optional<Colaborador> findById( UUID id );

    @Query(""" 
        select c from Colaborador 
            where c.matricula = :login.matricula
            and c.cpf = :login.cpf 
    """ )
    Optional<Colaborador> findByUsername( @Param( "login" ) ColaboradorUsername username );
 }