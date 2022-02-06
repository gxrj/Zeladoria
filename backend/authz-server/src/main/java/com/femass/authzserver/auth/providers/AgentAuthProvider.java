package com.femass.authzserver.auth.providers;

import com.femass.authzserver.auth.models.AgentCredentials;
import com.femass.authzserver.auth.tokens.AgentAuthToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AgentAuthProvider implements AuthenticationProvider {


    private UserDetailsService uds;
    private PasswordEncoder encoder;

    public AgentAuthProvider( UserDetailsService uds, PasswordEncoder encoder ) {
        this.uds = uds;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate( Authentication auth ){

        var username = auth.getName();
        var agentCredentials = ( AgentCredentials ) auth.getCredentials();

        var user = uds.loadUserByUsername( username );
        var passwordMatches = encoder.matches( agentCredentials.getPassword(), user.getPassword() );

        if( passwordMatches )
            return new AgentAuthToken( username, agentCredentials, user.getAuthorities() );
        else
            throw new BadCredentialsException( "Bad credentials" );
    }

    @Override
    public boolean supports( Class< ? > authToken ){
        return authToken.equals( AgentAuthToken.class );
    }
    
}
