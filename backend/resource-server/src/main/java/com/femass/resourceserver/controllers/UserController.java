package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.CallService;
import com.femass.resourceserver.services.UserService;

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
public class UserController {

    @Autowired
    private CallService callService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping( "/registration-user" )
    public ResponseEntity<JSONObject> registerUser( HttpServletRequest req ) throws IOException {

        var json = RequestHandler.parseToJson( req );
        var name = json.get( "name" ).asText();
        var username = json.get( "username" ).asText();

        if( userService.existsUserByUsername( username ) ){
            var body = new JSONObject();
            body.appendField( "message", "Email already in use" );
            return new ResponseEntity<>( body, HttpStatus.CONFLICT );
        }

        var password = passwordEncoder.encode( json.get( "password" ).asText() );
        var userRole = new SimpleGrantedAuthority( "ROLE_USER" );

        var entity = new UserEntity( username, password, List.of( userRole ) );
        entity.setName( name );

        var jsonBody = new JSONObject();

        if( userService.createOrUpdate( entity ) ) {
            jsonBody.appendField( "message", "Created" );
            return new ResponseEntity<>( jsonBody, HttpStatus.CREATED );
        }
        else {
            jsonBody.appendField( "message", "Error" );
            return new ResponseEntity<>( jsonBody, HttpStatus.INTERNAL_SERVER_ERROR );
        }
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