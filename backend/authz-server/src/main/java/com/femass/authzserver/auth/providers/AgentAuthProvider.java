package com.femass.authzserver.auth.providers;

import com.femass.authzserver.auth.models.domain.AgentCredentials;
import com.femass.authzserver.auth.services.AgentService;
import com.femass.authzserver.auth.tokens.AgentAuthToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AgentAuthProvider implements AuthenticationProvider {

    private final AgentService agentService;
    private final PasswordEncoder encoder;

    /* @AllArgsContructor is not taking effect on VSCode even with its lombok extension */
    public AgentAuthProvider( AgentService agentService, PasswordEncoder encoder ) {
        this.agentService = agentService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate( Authentication auth ) throws AuthenticationException {

        var username = auth.getName();
        var tokenCredentials = ( AgentCredentials ) auth.getCredentials();
        
        var account = agentService.findByUsername( username );
        var agentCredentials = account.getCredentials();

        var passwordMatches = encoder.matches( tokenCredentials.getPassword(),
                                                        agentCredentials.getPassword() );
                                                        
        var isAuthenticated = passwordMatches && agentService.checkCpf( tokenCredentials, agentCredentials );
        var isEnabled = account.getEnabled();
        
        if( isAuthenticated && isEnabled )
            return new AgentAuthToken( username, agentCredentials, account.getAuthorities() );
        else
            throw new BadCredentialsException( "Bad credentials" );
    }

    @Override
    public boolean supports( Class< ? > authToken ){
        return authToken.equals( AgentAuthToken.class );
    } 
}
