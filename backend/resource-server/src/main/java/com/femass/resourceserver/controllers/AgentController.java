package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.domain.Agent;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.AgentService;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping( "/agent/info" )
    public ResponseEntity<JSONObject> getUserInfo() {

        var subject = extractLoginFromJwt();

        if( subject == null ) {
            return new ResponseEntity<>(
                    new JSONObject()
                            .appendField( "message","no user authentication found" ),
                    HttpStatus.INTERNAL_SERVER_ERROR );
        }

        var agent = agentService.findByUsername( subject );
        var json = new JSONObject();

        json.appendField( "username", subject );
        json.appendField( "name", agent.getName() );
        json.appendField( "department", agent.getDepartment().getName() );

        return ResponseEntity.ok( json );
    }

    /**Gets Jwt from JwtAuthenticationToken stored into SecurityContextHolder<br>
     * and return its 'sub'(subject) claim in string format
     * */
    private String extractLoginFromJwt() {
        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext()
                                                                            .getAuthentication();
        if( authToken == null ) return null;

        var jwt = ( Jwt ) authToken.getPrincipal();

        return jwt.getClaim( "sub" );
    }

    @PostMapping( "/manager/new-agent" )
    public ResponseEntity<JSONObject> registerAgent( HttpServletRequest req ) throws IOException {

        var created = createAgent( req );
        var jsonBody = new JSONObject();

        if( created ) {
            jsonBody.appendField( "message", "Created" );
            return ResponseEntity.ok( jsonBody );
        }
        else {
            jsonBody.appendField( "message", "Error" );
            
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( jsonBody );
        }
    }

    private boolean createAgent( HttpServletRequest request ) throws IOException {

        var json = RequestHandler.parseToJson( request );

        var account = buildAccount( json );
        var name = json.get( "name" ).asText();
        var entity = new Agent( name, account );

        return agentService.createOrUpdate( entity );
    }

    private AgentAccount buildAccount( JsonNode json ) throws IOException {
        var username = json.get( "username" ).asText();

        if( agentService.existsAgentByUsername( username ) ) {
            throw new IOException( "Registration already in use" );
        }

        var password = passwordEncoder
                            .encode( json.get( "password" ).asText() );

        var cpf = json.get( "cpf" ).asText();
        var agentRole = new SimpleGrantedAuthority( "ROLE_AGENT" );
        var credentials = new AgentCredentials( password, cpf );

        return new AgentAccount( username, credentials, List.of( agentRole ) );
    }
}