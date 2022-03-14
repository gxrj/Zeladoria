package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Objects;

@RestController
@RequestMapping(
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallController {

    @Autowired
    private ServiceModule module;

    @PostMapping( "/anonymous/calls/new" )
    public ResponseEntity<JSONObject> create( @RequestBody CallDTO callDto ) {

        var call = CallDTO.deserialize( callDto, module );

        var json = new JSONObject();
        HttpStatus status;

        if( module.getCallService().createOrUpdate( call ) ) {
            json.appendField( "message", "call created!" );
            status = HttpStatus.CREATED;
        }
        else {
            json.appendField( "message", "error!" );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>( json, status );
    }

    @GetMapping( "/user/calls/all" )
    public ResponseEntity<JSONObject> listAllByLoggedUser() {

        try {
            var login = extractLoginFromContext();

            var calls = module.getCallService()
                    .findCallByAuthor( login )
                    .parallelStream().map( CallDTO::serialize ).toList();

            return getSuccessResponse( calls );
        } catch( AuthenticationException ex ) {

            return getInternalErrorMessage();
        }
    }

    @GetMapping( "/agent/calls/all" )
    public ResponseEntity<JSONObject> listCallsByAgentDeptartment() {

       try {
           var login = extractLoginFromContext();
           var agent = module.getAgentService().findByUsername( login );

           var calls = module.getCallService()
                   .findCallByDepartment( agent.getDepartment().getName() )
                   .parallelStream().map( CallDTO::serialize ).toList();

           return getSuccessResponse( calls );

       } catch ( AuthenticationException ex ) {

           return getInternalErrorMessage();
       }
    }

    private String extractLoginFromContext() throws AuthenticationException {

        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null )
            throw new AuthenticationException( "no user authentication found" );

        var jwt = ( Jwt ) authToken.getPrincipal();
        return jwt.getClaim( "sub" ).toString();
    }

    private ResponseEntity<JSONObject> getInternalErrorMessage() {

        return ResponseEntity
                .status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( new JSONObject()
                        .appendField( "message","no user authentication found" ) );
    }

    private ResponseEntity<JSONObject> getSuccessResponse( Object content ) {

        var json = new JSONObject();
        json.appendField("result", content );

        return new ResponseEntity<>( json, HttpStatus.ACCEPTED );
    }
}
