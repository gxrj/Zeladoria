package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.femass.resourceserver.domain.Citizen;
import com.femass.resourceserver.domain.account.CitizenAccount;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.CitizenService;;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping( "/registration-user" )
    public ResponseEntity<JSONObject> registerUser( HttpServletRequest req ) throws IOException {

        var created = createCitizen( req );
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

    private Boolean createCitizen( HttpServletRequest request ) throws IOException {

        var json = RequestHandler.parseToJson( request );

        var account = buildAccount( json );
        var name = json.get( "name" ).asText();
        var entity = new Citizen( name, account );

        return citizenService.createOrUpdate( entity );
    }

    private CitizenAccount buildAccount( JsonNode json ) throws IOException {

        var username = json.get( "username" ).asText();

        if( citizenService.existsCitizenByUsername( username ) ) {
            throw new IOException( "Email already in use" );
        }

        var password = passwordEncoder.encode( json.get( "password" ).asText() );
        var userRole = new SimpleGrantedAuthority( "ROLE_USER" );

        return new CitizenAccount( username, password, List.of( userRole ) );
    }
    
    @GetMapping( "/user/test" )
    public ResponseEntity<JSONObject> test() {
        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder
                                                            .getContext().getAuthentication();

        if( authToken == null ) {
            return new ResponseEntity<>(
                    new JSONObject()
                            .appendField( "message","no user authentication found" ),
                    HttpStatus.INTERNAL_SERVER_ERROR );
        }

        var jwt = ( Jwt ) authToken.getPrincipal();
        var subject = jwt.getClaim( "sub" );

        var json = new JSONObject();
        json.appendField( "message", "Authenticated" );
        json.appendField( "user_account", subject.toString() );

        return new ResponseEntity<>( json, HttpStatus.CREATED );
    }
}