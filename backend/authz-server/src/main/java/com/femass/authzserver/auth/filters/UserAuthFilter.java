package com.femass.authzserver.auth.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.authzserver.auth.tokens.UserAuthToken;
import com.femass.authzserver.auth.handlers.RequestHandler;

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

        String username, password;

        if( RequestHandler.isJsonContent( req ) ){
            
            var json = RequestHandler.parseToJson( req );

            username = json.get( "username" ).asText();
            password = json.get( "password" ).asText();
        }
        else {
            username = req.getParameter( "username" );
            password = req.getParameter( "password" );
        }

        var token = new UserAuthToken( username, password );

        return getAuthenticationManager().authenticate( token );
    }
}