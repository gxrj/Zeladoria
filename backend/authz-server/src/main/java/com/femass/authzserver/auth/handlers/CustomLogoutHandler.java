package com.femass.authzserver.auth.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutHandler implements LogoutSuccessHandler {

    private final String baseAddress;
    public CustomLogoutHandler( String baseAddress ) {
        this.baseAddress = baseAddress;
    }

    @Override
    public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication )
                                                    throws IOException, ServletException {
        var userType = request.getParameter( "user_type" );

        if ( userType != null && userType.equals( "agent" ) )
            response.sendRedirect( this.baseAddress + "/end-session-agent" );
        else
            response.sendRedirect( this.baseAddress + "/end-session" );
    }
}
