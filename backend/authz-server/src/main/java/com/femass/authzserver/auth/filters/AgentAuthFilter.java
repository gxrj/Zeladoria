package com.femass.authzserver.auth.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.authzserver.auth.models.AgentCredentials;
import com.femass.authzserver.auth.tokens.AgentAuthToken;
import com.femass.authzserver.utils.RequestHandler;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class AgentAuthFilter extends AbstractAuthenticationProcessingFilter {


    public AgentAuthFilter( RequestMatcher matcher, AuthenticationManager authManager ) {
        super( matcher, authManager );
    }
    
    @Override
    public Authentication attemptAuthentication( HttpServletRequest req, 
                                                 HttpServletResponse resp ) throws IOException {

        var username = RequestHandler.obtainParam( req, "username" );
        var cpf = RequestHandler.obtainParam( req, "cpf" );
        var password = RequestHandler.obtainParam( req, "password" );

        AgentCredentials credentials = new AgentCredentials( password, cpf );
        var token = new AgentAuthToken( username, credentials );

        return getAuthenticationManager().authenticate( token );  
    }   
}