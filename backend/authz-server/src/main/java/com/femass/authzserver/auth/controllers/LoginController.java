package com.femass.authzserver.auth.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getEndSession() { return "end-session"; }

    @GetMapping( path = "/end-session-agent" )
    public String getEndSessionAgent() { return "end-session-agent"; }
}