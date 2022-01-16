package com.femass.authserver.auth.providers;

import com.femass.authserver.auth.domain.model.ColaboradorAuthToken;
import com.femass.authserver.auth.domain.users.Colaborador;
import com.femass.authserver.auth.services.ColaboradorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
public class ColaboradorAuthProvider extends DaoAuthenticationProvider {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate( Authentication auth )
    throws AuthenticationException
    {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        var cpf = ( (ColaboradorAuthToken) auth ).getCpf();
        
        Colaborador user = colaboradorService.findUserByMatricula( username );

        var proceedAuthentication = passwordEncoder.matches( password, user.getSenha() )
                                    && cpf.equals( user.getCpf() );

        if( proceedAuthentication ) 
            return new ColaboradorAuthToken( username, password, cpf,
                                             user.getAuthorizacoes() );
        else
            throw new BadCredentialsException( "Bad credentials" );

    }

    @Override
    public boolean supports( Class<?> authClassType ){
        return authClassType.equals( ColaboradorAuthToken.class );
    }
}