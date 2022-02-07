package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.resourceserver.domain.AgentCredentials;
import com.femass.resourceserver.domain.AgentEntity;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.handlers.ResponseHandler;
import com.femass.resourceserver.services.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class AgentController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AgentService agentService;


    @PostMapping( "/registration-agent" ) /** Temporary endpoint */
    public HttpServletResponse registerAgent( HttpServletRequest req, 
                                              HttpServletResponse res ) 
            throws IOException {

        var username = RequestHandler.obtainParam( req, "username" );

        if( !agentService.existsAgentByUsername( username ) ) {
            ResponseHandler.prepareJsonResponse( res, 400, "Validation error, check your data" );
            return res;
        }    
        
        var password = RequestHandler.obtainParam( req, "password" );
        password = passwordEncoder.encode( password );

        var cpf = RequestHandler.obtainParam( req, "cpf" );
        
        AgentEntity entity = new AgentEntity( username, new AgentCredentials( password, cpf ), List.of( () -> "ROLE_AGENT" ) );

        var created = agentService.create( entity );

        if( created )  
            ResponseHandler.prepareJsonResponse( res, 201, "Created" );
        else 
            ResponseHandler.prepareJsonResponse( res, 500, "Error" );

        return res;
    }
}