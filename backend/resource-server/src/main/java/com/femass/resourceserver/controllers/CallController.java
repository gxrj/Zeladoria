package com.femass.resourceserver.controllers;

import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/user/calls",
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallController {

    @Autowired
    private CallService callService;

    @PostMapping( "/new" )
    public ResponseEntity<String> create( Call call ) {
        if( callService.createOrUpdate( call ) )
            return new ResponseEntity<>( "{\"message\": \"call created!\"}", HttpStatus.CREATED );
        else
            return new ResponseEntity<>( "{\"message\": \"error!\"}", HttpStatus.INTERNAL_SERVER_ERROR );
    }
}