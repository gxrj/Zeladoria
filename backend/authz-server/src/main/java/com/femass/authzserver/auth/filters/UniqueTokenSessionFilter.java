package com.femass.authzserver.auth.filters;

import com.femass.authzserver.auth.services.InMemoryTokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class UniqueTokenSessionFilter extends OncePerRequestFilter  {

    private final OAuth2AuthorizationService authorizationService;

    public UniqueTokenSessionFilter( OAuth2AuthorizationService authorizationService ) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void doFilterInternal( HttpServletRequest request,
                                  HttpServletResponse response, FilterChain filterChain )
                                                            throws ServletException, IOException {

        filterChain.doFilter( request, response );

        var token = request.getHeader( "authorization" );

        if( token != null  ) {

            if ( authorizationService instanceof InMemoryTokenService ) {
                authzServiceInMemory( request, response, authorizationService, token );
            }
            else if ( authorizationService instanceof JdbcOAuth2AuthorizationService ) {
                jdbcAuthzService( request, response, authorizationService, token );
            }
        }
    }

    private void authzServiceInMemory(  HttpServletRequest req, HttpServletResponse res,
                                        OAuth2AuthorizationService authorizationService, String token ) {

        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        try {
            Pattern regex = Pattern.compile( "[Bb][Ee][Aa][Rr][Ee][Rr] " );
            token = token.replaceAll( regex.pattern(), "" );

            OAuth2Authorization result = authorizationService
                                .findByToken( token, null );

            Assert.notNull( result, "token sessions are null" );

            System.out.println( "principal: " + result );
        }
        catch( Exception ex ) {
            System.out.println( "Exception: " + ex.getMessage()  );
        }
    }

    private void jdbcAuthzService( HttpServletRequest req, HttpServletResponse res,
                                   OAuth2AuthorizationService authorizationService, String token ) {
        System.out.println( "todo database" );
    }

    @Override
    public boolean shouldNotFilter( HttpServletRequest request ) {
        return !request.getRequestURI().equals( "/oauth2/token" );
    }
}