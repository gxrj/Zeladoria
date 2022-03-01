package com.femass.authzserver.auth.filters;

import com.femass.authzserver.auth.services.InMemoryTokenService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class UniqueTokenSessionFilter extends OncePerRequestFilter  {

    private final InMemoryTokenService authorizationService;

    public UniqueTokenSessionFilter( InMemoryTokenService authorizationService ) {
        this.authorizationService = authorizationService;
    }


    @Override
    public void doFilterInternal( HttpServletRequest request,
                                  HttpServletResponse response, FilterChain filterChain )
                                                            throws ServletException, IOException {

        filterChain.doFilter( request, response );


        var token = request.getHeader( "authorization" );
        var grantType = request.getParameter( "grant_type" );

        if( token != null  ) {

            authzServiceInMemory( request, response, authorizationService, token );
//            if ( authorizationService instanceof InMemoryTokenService ) {
//                authzServiceInMemory( request, response, authorizationService, token );
//            }
//            else if ( authorizationService instanceof JdbcOAuth2AuthorizationService ) {
//                jdbcAuthzService( request, response, authorizationService, token );
//            }
        }
    }

    private void authzServiceInMemory(  HttpServletRequest req, HttpServletResponse res,
                                        InMemoryTokenService authorizationService, String token ) {

        try {
            Pattern regex = Pattern.compile( "[Bb][Ee][Aa][Rr][Ee][Rr] " );
            token = token.replaceAll( regex.pattern(), "" );
            token = token.replaceAll( " ", "" );
            OAuth2Authorization result = authorizationService
                                .findByToken( token , new OAuth2TokenType( "access_token" ) );

            Assert.notNull( result, "OAuth2Authorization nulo " );
        }
        catch( Exception ex ) {
            System.out.println( "Exception: " + ex.getMessage()  );
        }
    }

    private void jdbcAuthzService( HttpServletRequest req, HttpServletResponse res,
                                   InMemoryTokenService authorizationService, String token ) {
        System.out.println( "todo database" );
    }

    @Override
    public boolean shouldNotFilter( HttpServletRequest request ) {
        return !request.getRequestURI().equals( "/oauth2/token" );
    }
}