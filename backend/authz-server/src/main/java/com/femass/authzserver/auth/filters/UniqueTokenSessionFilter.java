package com.femass.authzserver.auth.filters;

import com.femass.authzserver.auth.services.InMemoryTokenService;

import com.nimbusds.jose.JWSObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2TokenType;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

import java.util.HashMap;
import java.util.Map;

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
        var code = request.getParameter( "code" );

        //avoid multiple session creation
        if( code != null ) {

            var currentSession = authorizationService
                                    .findByToken( code, new OAuth2TokenType( "code" ) );

            if( currentSession != null ) {
                var currentToken = currentSession.getAccessToken().getToken().getTokenValue();
                var principal = ( Authentication ) currentSession.getAttribute( Principal.class.getName() );
                Assert.notNull( principal, "principal cannot be null" );
                var list = authorizationService.findByPrincipalName( principal.getName() );
                var olderSessions = list
                                    .parallelStream()
                                        .filter( session ->
                                                 !session.getAccessToken()
                                                            .getToken()
                                                                .getTokenValue()
                                                                    .equals( currentToken )
                                        )
                                        .toList();
                olderSessions.forEach( authorizationService::remove );
            }
        }
        // invalidate older tokens right after get a new one
//        if( token != null  ) {
//            inMemoryAuthzService( authorizationService, token );
//            if ( authorizationService instanceof InMemoryTokenService ) {
//                authzServiceInMemory( authorizationService, token );
//            }
//            else if ( authorizationService instanceof JdbcOAuth2AuthorizationService ) {
//                jdbcAuthzService( authorizationService, token );
//            }
//        }
    }

    //TODO: refactor inMemoryAuthzSerivce
    private void inMemoryAuthzService( InMemoryTokenService authorizationService, String token ) {

        try {
            // Remove "Bearer " from authorization header
            Pattern regex = Pattern.compile( "[Bb][Ee][Aa][Rr][Ee][Rr] " );
            token = token.replaceAll( regex.pattern(), "" );

            var jwsObject = JWSObject.parse( token );
            Assert.notNull( jwsObject, "OAuth2Authorization cannot be null " );
            //take a list of current user session tokens
            var username = (String) jwsObject.getPayload().toJSONObject().get( "sub" );
            var sessions = authorizationService.findByPrincipalName( username );
            Assert.notNull( sessions, "null jwt sessions" );

            Map< Long, OAuth2Authorization> list = new HashMap<>();

            long latest = 0;
            // builds a list with sessions mapped by its issued_at claim
            for( var session : sessions ) {
                 var accessToken = session.getAccessToken().getToken().getTokenValue();
                 try {
                     var obj = JWSObject.parse( accessToken );
                     var time = (String) obj.getPayload().toJSONObject().get( "iat" );
                     var key = Long.parseLong( time );
                     list.put( key, session );
                     if( key > latest ) latest = key;
                 }
                 catch ( ParseException ex ) {
                     System.out.println( ex.getMessage() );
                 }
            }
            // Remove the newest session
            list.remove( latest, list.get( latest ) );
            // Vanish the older sessions
            list.forEach( (key, value) -> authorizationService.remove( value ) );
        }
        catch( Exception ex ) {
            System.out.println( "Exception: " + ex.getMessage()  );
        }
    }

    private void jdbcAuthzService( InMemoryTokenService authorizationService, String token ) {
        System.out.println( "todo database" );
    }

    @Override
    public boolean shouldNotFilter( HttpServletRequest request ) {
        return !request.getRequestURI().equals( "/oauth2/token" );
    }
}