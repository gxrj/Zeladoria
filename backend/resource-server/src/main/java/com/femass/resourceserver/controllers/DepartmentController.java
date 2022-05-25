package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.DepartmentDTO;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private ServiceModule module;

    @GetMapping( "/manager/department/list" )
    public ResponseEntity<JSONObject> list() {
        var deptName = module.getAgentService()
                .findByUsername( extractLoginFromContext() )
                .getDepartment().getName();

        if( !deptName.equalsIgnoreCase( "Inova Macae" ) )
            return retrieveObject( new JSONObject(), "result", List.of() );

        var result  = module.getDepartmentService().findAll();

        return retrieveObject( new JSONObject(), "result", result );
    }

    @PostMapping( "/manager/department/edition" )
    public ResponseEntity<JSONObject> createOrUpdate( @RequestBody DepartmentDTO dto ) {
        var deptName = module.getAgentService()
                .findByUsername( extractLoginFromContext() )
                .getDepartment().getName();

        if( !deptName.equalsIgnoreCase( "Inova Macae" ) )
            return retrieveMessage( new JSONObject(), "fail", "Acesso negado" );

        var result  = module.getDepartmentService()
                                    .createOrUpdate( DepartmentDTO.deserialize( dto, module ) );
        var message = result ? "Secretaria atualizada com sucesso" : "Falha na edição";

        return retrieveObject( new JSONObject(), result ? "success" : "fail", message );
    }

    private String extractLoginFromContext() {

        var authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null ) return null;

        var jwt = (Jwt) authToken.getPrincipal();
        return jwt.getClaim( "sub" ).toString();
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