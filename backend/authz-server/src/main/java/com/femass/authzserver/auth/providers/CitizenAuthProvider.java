package com.femass.authzserver.auth.providers;

import com.femass.authzserver.auth.services.CitizenService;
import com.femass.authzserver.auth.tokens.CitizenAuthToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CitizenAuthProvider implements AuthenticationProvider {

    private final CitizenService citizenService;
    private final PasswordEncoder encoder;

    /* @AllArgsContructor is not taking effect on VSCode even with its lombok extension */
    public CitizenAuthProvider( CitizenService citizenService, PasswordEncoder encoder ) {
        this.citizenService = citizenService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate( Authentication auth ) 
        throws AuthenticationException {

        var username = auth.getName();
        var password = auth.getCredentials().toString();

        var account = citizenService.findByUsername( username );
        var passwordMatches = encoder.matches( password, account.getCredentials() );
        var isEnabled = account.getEnabled();

        if( passwordMatches && isEnabled )
            return new CitizenAuthToken( username, password, account.getAuthorities() );
        else
            throw new BadCredentialsException( "Bad credentials" );
    }

    @Override
    public boolean supports( Class< ? > authToken ) {
        return authToken.equals( CitizenAuthToken.class );
    }
}
