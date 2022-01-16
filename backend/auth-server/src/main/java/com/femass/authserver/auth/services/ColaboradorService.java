package com.femass.authserver.auth.services;

import java.util.function.Supplier;

import com.femass.authserver.auth.domain.users.Colaborador;
import com.femass.authserver.auth.repository.ColaboradorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorService {
    
    @Autowired
    private ColaboradorRepository repository;

    public Colaborador findUserByMatricula( String matricula ){

        
        Supplier< UsernameNotFoundException > exception = 
            () -> new UsernameNotFoundException( "Colaborador nao encontrado" );

        Colaborador user = repository.findByUsername( matricula )
                                        .orElseThrow( exception );

        return user;
    }
}
