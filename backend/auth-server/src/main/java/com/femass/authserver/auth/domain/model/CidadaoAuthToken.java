package com.femass.authserver.auth.domain.model;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter

public class CidadaoAuthToken extends UsernamePasswordAuthenticationToken {
    
    public CidadaoAuthToken( String usename, String password ){
        super( usename, password );
    }

    public CidadaoAuthToken( String usename, String password,
                             Collection<GrantedAuthority> autorizacoes ) {

        super( usename, password, autorizacoes );
    }
}