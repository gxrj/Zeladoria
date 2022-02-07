package com.femass.authzserver.auth.tokens;

import java.util.Collection;

import com.femass.authzserver.auth.models.domain.AgentCredentials;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AgentAuthToken extends UsernamePasswordAuthenticationToken {

    public AgentAuthToken( String principal, AgentCredentials credentials ) {
        super( principal, credentials );
    }

    public AgentAuthToken( String principal, AgentCredentials credentials,
                            Collection< ? extends GrantedAuthority > authorities  ) {

        super( principal, credentials, authorities );
    }
    
}