package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping( "/registration-user" )
    public String registerUser( HttpServletRequest req, 
                                             HttpServletResponse res )
            throws IOException {

        var json = RequestHandler.parseToJson( req );
        var name = json.get( "name" ).asText();
        var username = json.get( "username" ).asText();

        if( userService.existsUserByUsername( username ) ){
            return "Email already in use";
        }

        var password = passwordEncoder.encode( json.get( "password" ).asText() );
        var userRole = new SimpleGrantedAuthority( "ROLE_USER" );

        var entity = new UserEntity( username, password, List.of( userRole ) );
        entity.setName( name );
        
        if( userService.create( entity ) )
            return "{\"message\":\"Created\"}";
        else 
            return "{\"message\":\"Error\"}";
    }
    
    @GetMapping( "/user/test" )
    public String test() { return "{\"message\":\"Authenticated\"}"; }
}
