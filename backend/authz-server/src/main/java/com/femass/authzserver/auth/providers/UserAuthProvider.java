package com.femass.authzserver.auth.providers;

import com.femass.authzserver.auth.services.UserService;
import com.femass.authzserver.auth.tokens.UserAuthToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserAuthProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder encoder;

    /* @AllArgsContructor is not taking effect on VSCode even with its lombok extension */
    public UserAuthProvider( UserService userService, PasswordEncoder encoder ) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate( Authentication auth ) 
        throws AuthenticationException {

        var username = auth.getName();
        var password = auth.getCredentials().toString();

        var user = userService.findByUsername( username );
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