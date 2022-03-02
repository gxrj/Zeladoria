package com.femass.authzserver.auth.filters;

import com.femass.authzserver.auth.services.InMemoryTokenService;

import com.nimbusds.jose.JWSObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2TokenType;

import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
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
                                    .stream()
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
//            authzServiceInMemory( authorizationService, token );
//            if ( authorizationService instanceof InMemoryTokenService ) {
//                authzServiceInMemory( request, response, authorizationService, token );
//            }
//            else if ( authorizationService instanceof JdbcOAuth2AuthorizationService ) {
//                jdbcAuthzService( authorizationService, token );
//            }
//        }
    }

    private void authzServiceInMemory( InMemoryTokenService authorizationService, String token ) {

        try {

            Pattern regex = Pattern.compile( "[Bb][Ee][Aa][Rr][Ee][Rr] " );
            token = token.replaceAll( regex.pattern(), "" );

            var jwsObject = JWSObject.parse( token );
            Assert.notNull( jwsObject, "OAuth2Authorization nulo " );

            var username = (String) jwsObject.getPayload().toJSONObject().get( "sub" );
            var jwtSessions = authorizationService.findByPrincipalName( username );
            Assert.notNull( jwtSessions, "null jwt sessions" );

            jwtSessions.forEach(
                    jwtSession -> {
                        var access = jwtSession.getAccessToken().getToken().getTokenValue();
                        var refresh = jwtSession.getRefreshToken();
                        Assert.notNull( refresh, "refresh is null" );
                        System.out.println( "access: "+access+"\nrefresh: "+ refresh.getToken().getTokenValue() );
                    }
            );

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