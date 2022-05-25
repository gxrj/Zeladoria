package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.DutyDTO;
import com.femass.resourceserver.dto.DutyGroupDTO;
import com.femass.resourceserver.services.ServiceModule;

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
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class DutyGroupController {

    @Autowired
    private ServiceModule module;

    @GetMapping( "/anonymous/duty/categories" )
    public ResponseEntity<JSONObject> getDutyGroupList() {

        var dutyGroupService = module.getDutyGroupService();
        var dutyService = module.getDutyService();

        var list = dutyGroupService
                    .findAllDutyGroup().parallelStream()
                    .map(
                        group -> {
                            var json = new JSONObject();

                            json.appendField( "category", group.getName() );

                            var duties = dutyService.findDutyByCategory( group.getName() )
                                                        .parallelStream().map( DutyDTO::serialize ).toList();

                            json.appendField( "services", duties );

                            return json;
                        }
                    ).toList();

        var json = new JSONObject()
                        .appendField( "result", list );

        return new ResponseEntity<>( json, HttpStatus.OK );
    }

    @PostMapping( path = { "/manager/category/new", "/manager/category/edition" } )
    public ResponseEntity<JSONObject> createOrUpdate( @RequestBody DutyGroupDTO categoryDto ) {
        var subject = extractLoginFromContext();

        if( subject == null )
            return retrieveMessage( new JSONObject(), "fail", "no user authentication found" );

        var categoryService = module.getDutyGroupService();
        var entity = DutyGroupDTO.deserialize( categoryDto, module );
        var dept = module.getAgentService().findByUsername( subject ).getDepartment();
        var needsBlocking = !dept.getName().equalsIgnoreCase( "Inova Macae" );

        if( needsBlocking )
            return retrieveMessage( new JSONObject(), "fail", "Não autorizado" );

        var result = categoryService.createOrUpdate( entity );
        var message = result ? "Categoria atualizada com sucesso" : "Categoria não atualizada";

        return retrieveMessage( new JSONObject(), result ? "success" : "fail", message );
    }

    private String extractLoginFromContext() {

        var authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null ) return null;

        var jwt = (Jwt) authToken.getPrincipal();
        return jwt.getClaim( "sub" ).toString();
    }

    private ResponseEntity<JSONObject> retrieveMessage(
            JSONObject jsonBody, String messageType, String message ) {
        jsonBody.appendField( "message", message );
        return messageType
                .equalsIgnoreCase( "success" ) ?
                ResponseEntity.ok( jsonBody )
                : ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( jsonBody );
    }

    private ResponseEntity<JSONObject> retrieveObject(
            JSONObject jsonBody, String objectName, Object entity ) {
        jsonBody.appendField( objectName, entity );
        return ResponseEntity.ok( jsonBody );
    }
}