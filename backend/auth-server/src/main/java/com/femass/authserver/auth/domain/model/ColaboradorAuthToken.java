package com.femass.authserver.auth.domain.model;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter

public class ColaboradorAuthToken extends UsernamePasswordAuthenticationToken {
    
    private String cpf;

    public ColaboradorAuthToken( String username, String password, String cpf ){
        super( username, password );
        this.cpf = cpf;
    }

    public ColaboradorAuthToken( String username, String password, String cpf, 
                                 Collection<GrantedAuthority> autorizacoes )
    {
        super( username, password, autorizacoes );
        this.cpf = cpf;
    }
}