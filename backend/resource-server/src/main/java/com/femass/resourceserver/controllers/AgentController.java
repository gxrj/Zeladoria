package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.domain.Agent;
import com.femass.resourceserver.dto.AgentDTO;
import com.femass.resourceserver.dto.DepartmentDTO;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.AgentService;

import com.femass.resourceserver.services.ServiceModule;
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
    private ServiceModule module;

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

        var agent = module.getAgentService().findByUsername( subject );
        var json = new JSONObject();

        json.appendField( "result", AgentDTO.serialize( agent ) );

        return ResponseEntity.ok( json );
    }

    @PostMapping( "/manager/new-agent" )
    public ResponseEntity<JSONObject> registerAgent( @RequestBody AgentDTO entity ) {

        var jsonBody = new JSONObject();
        var login = extractLoginFromJwt();

        if( login == null ) {
            jsonBody.appendField( "message", "Nao autenticado" );
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( jsonBody );
        }

        var adminDeptName = "Inova Macae";
        var agentService = module.getAgentService();
        var agent = agentService.findByUsername( login );
        var agentDeptName = agent.getDepartment().getName();

        if( !agentDeptName.equalsIgnoreCase( adminDeptName ) )
            entity.setDepartment( new DepartmentDTO( agentDeptName ) );

        var created = agentService.createOrUpdate( AgentDTO.deserialize( entity, module) );

        if( created ){
            jsonBody.appendField( "message", "Colaborador criado com sucesso!" );
            return ResponseEntity.ok( jsonBody );
        }

        jsonBody.appendField( "message", "Falha no cadastramento!" );
        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR )
                                .body( jsonBody );

    }

    /**Gets Jwt from JwtAuthenticationToken stored into SecurityContextHolder<br>
     * and returns Jwt´s 'sub'(subject) claim in string format
     * */
    private String extractLoginFromJwt() {
        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext()
                                                                            .getAuthentication();
        if( authToken == null ) return null;

        var jwt =  ( Jwt ) authToken.getPrincipal();
        return jwt.getClaim( "sub" );
    }
}