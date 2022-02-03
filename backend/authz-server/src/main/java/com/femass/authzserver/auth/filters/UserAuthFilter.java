package com.femass.authzserver.auth.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.authzserver.auth.tokens.UserAuthToken;
import com.femass.authzserver.utils.RequestHandler;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UserAuthFilter extends AbstractAuthenticationProcessingFilter {
    
    public UserAuthFilter( RequestMatcher matcher, AuthenticationManager authManager ) {
        super( matcher, authManager );
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest req, HttpServletResponse resp )
        throws IOException {

        var username = RequestHandler.obtainParam( req, "username" );
        var password = RequestHandler.obtainParam( req, "password" );

        var token = new UserAuthToken( username, password );

        return getAuthenticationManager().authenticate( token );
    }
}