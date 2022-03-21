package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.DutyDTO;
import com.femass.resourceserver.services.ServiceModule;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class DutyController {

    @Autowired
    private ServiceModule module;

    @GetMapping("/anonymous/duty/all")
    public ResponseEntity<JSONObject> getDuties(){

        var dutyService = module.getDutyService();

        var list = dutyService
                .findAllDuties().parallelStream()
                .map( DutyDTO::serialize ).toList();

        var json = new JSONObject()
                .appendField( "result", list );

        return new ResponseEntity<>( json, HttpStatus.OK );
    }
}