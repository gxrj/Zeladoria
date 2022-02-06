package com.femass.authzserver.auth.providers;

import com.femass.authzserver.auth.tokens.UserAuthToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAuthProvider implements AuthenticationProvider {

    private UserDetailsService uds;
    private PasswordEncoder encoder;

    public UserAuthProvider( UserDetailsService uds, PasswordEncoder encoder ) {
        this.uds = uds;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate( Authentication auth ) {

        var username = auth.getName();
        var password = auth.getCredentials().toString();

        var user = uds.loadUserByUsername( username );
        var passwordMatches = encoder.matches( password, user.getPassword() );

        if( passwordMatches )
            return new UserAuthToken( username, password, user.getAuthorities() );
        else
            throw new BadCredentialsException( "Bad credentials" );
    }

    @Override
    public boolean supports( Class< ? > authToken ) {
        return authToken.equals( UserAuthToken.class );
    }
    
}