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
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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
        //Todo: Refactor this method with CitizenDTO and CitizenAccountDTO
        var created = createCitizen( req );
        var jsonBody = new JSONObject();

        if( created ) {
            jsonBody.appendField( "message", "Cadastro realizado com sucesso!" );
            return ResponseEntity.ok( jsonBody );
        }
        else {
            jsonBody.appendField( "message", "Erro, cadastro não realizado!" );
            
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

        var username = json.get( "email" ).asText();

        if( citizenService.existsCitizenByUsername( username ) ) {
            throw new IOException( "Email already in use" );
        }

        var password = passwordEncoder.encode( json.get( "password" ).asText() );
        var userRole = new SimpleGrantedAuthority( "ROLE_USER" );

        return new CitizenAccount( username, password, List.of( userRole ) );
    }
    
    @GetMapping( "/user/info" )
    public ResponseEntity<JSONObject> getAccountInfo() {

        var subject = extractLoginFromJwt();

        if( subject == null ) {
            return new ResponseEntity<>(
                    new JSONObject()
                            .appendField( "message","no user authentication found" ),
                    HttpStatus.INTERNAL_SERVER_ERROR );
        }

        var citizen = citizenService.findByUsername( subject );

        var json = new JSONObject();
        json.appendField( "name", citizen.getName() );
        json.appendField( "username", citizen.getAccount().getUsername() );

        return new ResponseEntity<>( json, HttpStatus.CREATED );
    }

    /**
     * Gets Jwt from JwtAuthenticationToken stored into SecurityContextHolder<br>
     * and return its 'sub'(subject) claim in string format
     * */
    private String extractLoginFromJwt() {

        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder
                                                        .getContext().getAuthentication();

        if( authToken == null ) return null;

        var jwt = ( Jwt ) authToken.getPrincipal();

        return jwt.getClaim( "sub" );
    }
}
