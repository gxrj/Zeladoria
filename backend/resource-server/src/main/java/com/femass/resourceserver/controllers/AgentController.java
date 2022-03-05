package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.resourceserver.domain.user.AgentCredentials;
import com.femass.resourceserver.domain.user.AgentEntity;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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


    @PostMapping( "/manager/new-agent" )
    public String registerAgent( HttpServletRequest req, 
                                              HttpServletResponse res ) 
            throws IOException {

        var json = RequestHandler.parseToJson( req );
        var name = json.get( "name" ).asText();
        var username = json.get( "username" ).asText();

        if( agentService.existsAgentByUsername( username ) ) {
            return "Registration already in use";
        }    
        
        var password = json.get( "password" ).asText();
        password = passwordEncoder.encode( password );

        var cpf = json.get( "cpf" ).asText();
        var agentRole = new SimpleGrantedAuthority( "ROLE_AGENT" );

        AgentEntity entity = new AgentEntity( username, new AgentCredentials( password, cpf ), List.of( agentRole ) );
        entity.setName( name );
        
        var created = agentService.createOrUpdate( entity );

        if( created )  
            return "{\"message\":\"Created\"}";
        else 
            return "{\"message\":\"Error\"}";
    }
}