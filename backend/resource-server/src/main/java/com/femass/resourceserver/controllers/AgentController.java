package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.AgentDTO;
import com.femass.resourceserver.dto.DepartmentDTO;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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

        if( subject == null )
            return retrieveMessage( new JSONObject(), "fail", "no user authentication found" );

        var agent = module.getAgentService().findByUsername( subject );

        return retrieveObject( new JSONObject(), "result", AgentDTO.serialize( agent ) );
    }

    @PostMapping( "/agent/account/edition" )
    public ResponseEntity<JSONObject> update( @RequestBody AgentDTO dto ) {
        var username = extractLoginFromJwt();
        var entity = AgentDTO.deserialize( dto, module );

        if( username != null && entity.getAccount().getUsername().equalsIgnoreCase( username ) ) {
            var result = module.getAgentService().createOrUpdate( entity );
            var message = result ? "Conta atualizada com sucesso" : "Falha na atualização de conta";
            return retrieveMessage( new JSONObject(), result ? "success" : "fail", message );
        }
        return retrieveMessage( new JSONObject(), "fail", "Falha, conflito de sessão" );
    }

    @GetMapping( "/manager/agent/list" )
    public ResponseEntity<JSONObject> listAgents() {

        var login = extractLoginFromJwt();

        if( login == null )
            return retrieveMessage( new JSONObject(), "fail", "Nao autenticado" );

        var agentService = module.getAgentService();
        var deptName = agentService.findByUsername( login ).getDepartment().getName();
        var result = agentService.listAgents( deptName )
                                                    .parallelStream().map( AgentDTO::serialize )
                                                        .toList();

        return retrieveObject( new JSONObject(), "result", result );
    }

    @PostMapping( "/manager/new-agent" )
    public ResponseEntity<JSONObject> registerAgent( @RequestBody AgentDTO entity ) {

        var login = extractLoginFromJwt();

        if( login == null )
            return retrieveMessage( new JSONObject(), "fail", "Nao autenticado" );

        var adminDeptName = "Inova Macae";
        var agentService = module.getAgentService();
        var agentDeptName =  agentService.findByUsername( login ).
                                                    getDepartment().getName();

        if( !agentDeptName.equalsIgnoreCase( adminDeptName ) )
            entity.setDepartment( new DepartmentDTO( agentDeptName ) );

        var created = agentService.createOrUpdate( AgentDTO.deserialize( entity, module ) );

        if( !created )
            return retrieveMessage( new JSONObject(), "fail", "Falha no cadastramento!" );

        return retrieveMessage( new JSONObject(), "success", "Colaborador criado com sucesso!" );
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

    private ResponseEntity<JSONObject> retrieveMessage(
                                        JSONObject jsonBody, String messageType, String message ) {
        jsonBody.appendField( "message", message );
        return messageType
                    .equalsIgnoreCase( "success" ) ?
                        ResponseEntity.ok( jsonBody )
                        : ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR )
                                            .body( jsonBody );
    }

    private ResponseEntity<JSONObject> retrieveObject(
                                        JSONObject jsonBody, String objectName, Object entity ) {
        jsonBody.appendField( objectName, entity );
        return ResponseEntity.ok( jsonBody );
    }
}