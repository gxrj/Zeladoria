package com.femass.authserver.auth.providers;

import com.femass.authserver.auth.domain.model.CidadaoAuthToken;
import com.femass.authserver.auth.domain.users.Cidadao;

import com.femass.authserver.auth.services.CidadaoService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
public class CidadaoAuthProvider implements AuthenticationProvider{

    @Autowired
    private CidadaoService cidadaoService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate( Authentication auth )
    throws AuthenticationException
    {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        
        Cidadao user = cidadaoService.findByUsername( username );

        var passwordMatches = encoder.matches( password, user.getSenha() );

        if( passwordMatches )
            return new CidadaoAuthToken( user.getNome(), user.getSenha(), 
                                         user.getAuthorizacoes() );
        else
            throw new BadCredentialsException( "Bad credentials" );
    }

    @Override
    public boolean supports( Class<?> authClassType ){
        return authClassType.equals( CidadaoAuthToken.class );
    }   
}