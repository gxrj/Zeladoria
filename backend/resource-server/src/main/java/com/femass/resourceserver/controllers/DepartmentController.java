package com.femass.resourceserver.controllers;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DepartmentController {

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