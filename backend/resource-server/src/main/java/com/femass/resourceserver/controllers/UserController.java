package com.femass.resourceserver.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.femass.resourceserver.domain.UserEntity;
import com.femass.resourceserver.handlers.RequestHandler;
import com.femass.resourceserver.handlers.ResponseHandler;
import com.femass.resourceserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public HttpServletResponse registerUser( HttpServletRequest req, 
                                             HttpServletResponse res )
            throws IOException {

        var username = RequestHandler.obtainParam( req, "username" );

        if( !userService.existsUserByUsername( username ) ){
            ResponseHandler.prepareJsonResponse( res, 400, "Validation error, check your data" );
            return res;
        }

        var password = passwordEncoder.encode( RequestHandler.obtainParam( req, "password" ) );
        var entity = new UserEntity( username, password, List.of( () -> "ROLE_AGENT" ) );
        
        if( userService.create( entity ) )
            ResponseHandler.prepareJsonResponse( res, 201, "Created" );
        else 
            ResponseHandler.prepareJsonResponse( res, 500, "Error" );
        
        return res;
    }
}