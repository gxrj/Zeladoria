package com.femass.authzserver.auth.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller

@RequestMapping(
    consumes = MediaType.ALL_VALUE,
    produces = MediaType.ALL_VALUE
)
public class LoginController {

    @GetMapping( path = "/login" )
    public String getUserFormLogin() { return "login"; }

    @GetMapping( path = "/agent/login" )
    public String getAgentFormLogin() { return "login-agent"; }

    @GetMapping( path = "/error" )
    public String getErrorPage() { return "error"; }  

    @GetMapping( path = "/unauthorized" )
    public String getUnauthorizedPage() { return "unauthorized"; }

    @GetMapping( path = "/end-session" )
    public HttpServletResponse getEndSession( HttpServletResponse response ) throws IOException {
        response.sendRedirect( "http://localhost:8100" );
        return response ;
    }

    @GetMapping( path = "/end-session-agent" )
    public HttpServletResponse getEndSessionAgent( HttpServletResponse response ) throws IOException {
        response.sendRedirect( "http://localhost:8100" );
        return response ;
    }
}