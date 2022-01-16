package com.femass.authserver.auth.services;

import java.util.function.Supplier;

import com.femass.authserver.auth.domain.users.Cidadao;
import com.femass.authserver.auth.repository.CidadaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CidadaoService {

    @Autowired
    private CidadaoRepository repository;


    public Cidadao findByUsername( String email ) {

        Supplier< UsernameNotFoundException > exception = 
            () -> new UsernameNotFoundException( "Cidadao nao encontrado" );


        Cidadao user = repository.findByUsername( email )
                                .orElseThrow( exception );

        return user;
    }
}