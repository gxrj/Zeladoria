package com.femass.resourceserver.controllers;

import com.femass.resourceserver.services.ServiceModule;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class DistrictController {

    @Autowired
    private ServiceModule module;

    @GetMapping( "/anonymous/district/all" )
    public ResponseEntity<JSONObject> loadDistricts() {
        var districts = module.getDistrictService().loadAll();

        var json = new JSONObject();
        json.appendField( "result", districts );

        return new ResponseEntity<>( json, HttpStatus.OK );
    }
}