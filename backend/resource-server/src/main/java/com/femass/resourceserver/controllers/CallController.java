package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    path = "/user/calls",
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallController {

    @Autowired
    private ServiceModule module;

    @PostMapping( "/new" )
    public ResponseEntity<JSONObject> create( @RequestBody CallDTO callDto ) {

        var call = CallDTO.deserialize( callDto, module );

        var json = new JSONObject();

        if( module.getCallService().createOrUpdate( call ) ) {
            json.appendField( "message", "call created!" );
            return new ResponseEntity<>( json, HttpStatus.CREATED );
        }
        else {
            json.appendField( "message", "error!" );
            return new ResponseEntity<>( json, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @GetMapping( "/all" )
    public ResponseEntity<JSONObject> listAllByLoggedUser() {

        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null ){
            return new ResponseEntity<>(
                    new JSONObject()
                            .appendField( "message","no user authentication found" ),
                    HttpStatus.INTERNAL_SERVER_ERROR );
        }

        var jwt = ( Jwt ) authToken.getPrincipal();
        var calls = module.getCallService()
                                    .findCallByAuthor( jwt.getClaim( "sub" ).toString() );

        var json = new JSONObject();
        json.appendField( "result", calls );

        return new ResponseEntity<>( json, HttpStatus.ACCEPTED );
    }
}