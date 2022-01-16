package com.femass.authserver.auth.repository;

import java.util.Optional;
import java.util.UUID;


import com.femass.authserver.auth.domain.users.Cidadao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CidadaoRepository extends JpaRepository<Cidadao, UUID> { 

    Optional<Cidadao> findById( UUID id );

    @Query( """
        select c from Cidadao c
            where c.email = :email 
    """ )
    Optional<Cidadao> findByUsername( @Param( "email" ) String username );
}