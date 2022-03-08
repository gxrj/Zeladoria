package com.femass.resourceserver.controllers;

import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.services.CallService;
import com.femass.resourceserver.services.DutyService;
import com.femass.resourceserver.services.UserService;
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
    private CallService callService;

    @Autowired
    private UserService userService;

    @Autowired
    private DutyService dutyService;

    @PostMapping( "/new" )
    public ResponseEntity<JSONObject> create( @RequestBody CallDTO callDto ) {

        var call = toDomainObject( callDto );

        var json = new JSONObject();

        if( callService.createOrUpdate( call ) ) {
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
        var calls = callService.findCallByAuthor( jwt.getClaim( "sub" ).toString() );

        var json = new JSONObject();
        json.appendField( "result", calls );

        return new ResponseEntity<>( json, HttpStatus.ACCEPTED );
    }

    private Call toDomainObject( CallDTO callDto ) {

        Call object;
        var id = callDto.getId();

        if( id != null )
            object = callService.findCallById( id );
        else {
            object = new Call(); /* The attributes bellow cannot change after first creation */
            object.setAuthor( userService.findByUsername( callDto.getAuthor().getUsername() ) );
            object.setPostingDate( callDto.getCreatedAt() );
        }

        object.setDuty( dutyService.findDutyByDescription( callDto.getDuty().getDescription() ) );
        object.setStatus( callDto.getStatus() );
        object.setDescription( callDto.getDescription() );
        object.setAddress( callDto.getAddress() );
        object.setImages( callDto.getImages() );

        return object;
    }
}