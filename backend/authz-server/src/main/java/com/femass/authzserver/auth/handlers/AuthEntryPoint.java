package com.femass.authzserver.auth.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Responsible to handle which form login
 * will be shown according to user type
 * when /oauth2/authorized is hit by an
 * unnauthenticated user.
 */
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence( HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authException ) throws
            IOException, ServletException {

        if ( endpointMatches( request, "/oauth2/authorize" ) ) {
            var userType = getUserType( request );
            redirectToLoginPageByUserType( response, userType );
        }
    }

    private boolean endpointMatches( HttpServletRequest request, String endpoint ) {
        return request.getRequestURI().equalsIgnoreCase( endpoint );
    }

    private String getUserType( HttpServletRequest request ) throws IOException {

        if (RequestHandler.isJsonContent(request))
            return RequestHandler.parseToJson(request).get("user_type").asText();
        else
            return request.getParameter("user_type");
    }

    private void redirectToLoginPageByUserType( HttpServletResponse response, String userType )
        throws IOException {

        if ( userType.equalsIgnoreCase( "agent" ) )
            response.sendRedirect( "/agent/login" );
        else
            response.sendRedirect( "/login" );
    }
}