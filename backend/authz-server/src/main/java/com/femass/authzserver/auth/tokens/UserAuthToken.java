package com.femass.authzserver.auth.tokens;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthToken extends UsernamePasswordAuthenticationToken {
    

    public UserAuthToken( String principal, String credentials ) {
        super( principal, credentials );
    }

    public UserAuthToken( String principal, String credentials, 
                          Collection< ? extends GrantedAuthority > authorities ) {

        super( principal, credentials, authorities );
    }
}