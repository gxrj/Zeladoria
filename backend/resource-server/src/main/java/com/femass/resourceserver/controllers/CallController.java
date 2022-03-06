package com.femass.resourceserver.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/user/calls",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.ALL_VALUE
)
public class CallController {

    //TODO
    @PostMapping( "/new" )
    public String create() {
        return "{\"message\": \"call created!\"}";
    }
}