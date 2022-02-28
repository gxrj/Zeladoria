package com.femass.authzserver.auth.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RevocationResponseHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthorizationService authorizationService;

    public RevocationResponseHandler ( OAuth2AuthorizationService authorizationService ) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request,
                                         HttpServletResponse response, Authentication auth )
        throws IOException {

        var tokenParameter = request.getParameter("token");
        var tokenType = request.getParameter("token_type_hint");

        var isAccessTokenType = tokenType != null
                && tokenType.equalsIgnoreCase("access_token");

        var isRefreshTokenType = tokenType != null
                && tokenType.equalsIgnoreCase( "refresh_token" );

        if( isAccessTokenType || isRefreshTokenType ) {

            var authorization = this.authorizationService
                    .findByToken( tokenParameter, new OAuth2TokenType( tokenType ) );
            Assert.notNull( authorization, "no authorization found" );
            this.authorizationService.remove( authorization );

            var writer = response.getWriter();
            writer.print( authorization );
            writer.close();
        }
        response.setStatus( HttpStatus.OK.value() );
    }
}
