package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.DutyDTO;
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
public class DutyController {

    @Autowired
    private ServiceModule module;

    @GetMapping( "/anonymous/duty/all" )
    public ResponseEntity<JSONObject> getDuties(){

        var dutyService = module.getDutyService();

        var list = dutyService
                .findAllDuties().parallelStream()
                .map( DutyDTO::serialize ).toList();

        return retrieveObject( new JSONObject(), "result", list );
    }

    @PostMapping( path = { "/manager/duty/new", "/manager/duty/edition" } )
    public ResponseEntity<JSONObject> createDuty( @RequestBody DutyDTO dutyDto ) {
        var subject = extractLoginFromContext();

        if( subject == null )
            return retrieveMessage( new JSONObject(), "fail", "no user authentication found" );

        var agent = module.getAgentService().findByUsername( subject );
        var dutyService = module.getDutyService();
        var entity = DutyDTO.deserialize( dutyDto, module );
        var dept = agent.getDepartment();

        if( !dept.getName().equalsIgnoreCase( "Inova Macae" ) )
            entity.setDepartment( dept );

        var result = dutyService.createOrUpdate( entity );
        var message =  result ? "Serviço cadastrado/atualizado com sucesso"
                                : "Falha na criação/autialização do serviço";

        return retrieveMessage( new JSONObject(), "fail", message );
    }


    private String extractLoginFromContext() {

        var authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null ) return null;

        var jwt = ( Jwt ) authToken.getPrincipal();
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