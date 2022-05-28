package com.femass.authzserver.auth.filters;

import com.femass.authzserver.auth.services.InMemoryTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

public class CustomLogoutFilter extends OncePerRequestFilter {

    private final InMemoryTokenService authorizationService;

    public CustomLogoutFilter( InMemoryTokenService authorizationService ) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain ) throws ServletException, IOException {

        var token = req.getParameter( "token" );
        Assert.notNull( token, "token cannot be null" );
        var currentSession = getAuthorization( token, checkTokenType( req ) );
        var principal = ( Authentication ) currentSession.getAttribute( Principal.class.getName() );
        Assert.notNull( principal, "principal cannot be null" );

        chain.doFilter( req, res );

        var list = authorizationService.findByPrincipalName( principal.getName() );
        list.stream().map( OAuth2Authorization::getRegisteredClientId ).forEach( System.out::println );
        list.forEach( authorizationService::remove );
    }

    private OAuth2TokenType checkTokenType( HttpServletRequest req ) {
        var hint = req.getParameter( "token_type_hint" );

        if( hint != null )
            return new OAuth2TokenType( hint );

        return null;
    }

    private OAuth2Authorization getAuthorization( String token, OAuth2TokenType type ) {
        if( type != null )
            return authorizationService.findByToken( token, type );

        var result = authorizationService
                                        .findByToken( token, new OAuth2TokenType( "access_token" ) );

        if( result == null )
            return authorizationService
                    .findByToken( token, new OAuth2TokenType( "refresh_token" ) );

        return result;
    }

    @Override
    public boolean shouldNotFilter( HttpServletRequest request ) {
        return !request.getRequestURI().equals( "/oauth2/revoke" );
    }
}