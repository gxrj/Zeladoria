package com.femass.resourceserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.dto.AgentDTO;
import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.services.FileStorageService;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import javax.ws.rs.PathParam;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallController {

    @Autowired
    private ServiceModule module;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private FileStorageService fileService;

    private final Logger LOG = LoggerFactory.getLogger( CallController.class );

    @PostMapping( "/anonymous/calls/new" )
    public ResponseEntity<JSONObject> create(
            @RequestParam( name = "call" ) String plainCall,
            @RequestParam( name = "files", required = false ) MultipartFile[] images ) {

        HttpStatus status;
        var entity = fromPlainToEntity( plainCall );
        var json = new JSONObject();

        if( entity != null ) {

            var username = sanitizeUsername( entity.getAuthor().getAccount().getUsername() );
            saveImages( images, username, entity.getProtocol() );

            json.appendField( "call", entity )
                .appendField( "message", "Ocorrência gravada com sucesso!" );
            status = HttpStatus.CREATED;
        }
        else {
            json.appendField( "message", "Falha na gravação da ocorrência!" );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>( json, status );
    }

    private Call fromPlainToEntity( String plainCall ) {
        try{
            var callDto = mapper.readValue( plainCall, CallDTO.class );
            var call = CallDTO.deserialize( callDto, module );
            return module.getCallService().persist( call );
        }
        catch ( JsonProcessingException ex ) {
            LOG.error( "Error parsing entity from plain text: {}", ex.getMessage() );
            return null;
        }
    }

    private String sanitizeUsername( String email ) {
        email = email.equalsIgnoreCase("anonimo@fiscaliza.com" )? "anonimo" : email;
        return email.replaceAll( "[^\\w]", "_" );
    }

    private void saveImages( MultipartFile[] images, String username, String callProtocol ) {

        if( images != null && images.length > 0 ) {
            Arrays.stream( images ).parallel()
                    .forEach( image -> fileService.store( image, username, callProtocol ) );
        }
    }

    @PostMapping(
        path = "/authenticated/call/file?{filename}",
        produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE  } )
    public @ResponseBody byte[] downloadFile(
            @RequestBody CallDTO callDto, @PathParam( "filename" ) String filename ) {

        var filePath = sanitizeUsername( callDto.getAuthor().getEmail() ) + "/" +
                       callDto.getProtocol() + "/" + filename;
        try {
            var resource = fileService.loadAsResource( filePath );
            return IOUtils.toByteArray( resource.getInputStream() );
        }
        catch ( IOException ex ) {
            LOG.error( "Erro, arquivo não encontrado ou inexistente: {}", ex.getMessage() );
            return null;
        }
    }

    @PostMapping( "/agent/calls/all" )
    public ResponseEntity<JSONObject> listCallsByAgentDeptartment( @RequestBody AgentDTO agent ) {

        var calls = module.getCallService()
                .findCallByDestination( agent.getDepartment().getName() )
                .parallelStream().map( CallDTO::serialize ).toList();

        return getSuccessResponse( calls );
    }

    @GetMapping( "/user/calls/all" )
    public ResponseEntity<JSONObject> listAllByLoggedUser() {

        try {
            var login = extractLoginFromContext();

            var calls = module.getCallService()
                    .findCallByAuthor( login )
                    .parallelStream().map( CallDTO::serialize ).toList();

            return getSuccessResponse( calls );
        } catch( AuthenticationException ex ) {

            return getInternalErrorMessage();
        }
    }

    @GetMapping( "/agent/calls/all" )
    public ResponseEntity<JSONObject> listCallsByAgentDeptartment() {

       try {
           var login = extractLoginFromContext();
           var agent = module.getAgentService().findByUsername( login );

           var calls = module.getCallService()
                   .findCallByDestination( agent.getDepartment().getName() )
                   .parallelStream().map( CallDTO::serialize ).toList();

           return getSuccessResponse( calls );

       } catch ( AuthenticationException ex ) {

           return getInternalErrorMessage();
       }
    }

    @PostMapping( "/agent/calls/deletion" )
    public ResponseEntity<JSONObject> removeCall( @RequestBody CallDTO dto ) {
        var call = CallDTO.deserialize( dto, module );
        var json = new JSONObject();
        HttpStatus status;
        try {
            var path = sanitizeUsername(
                    call.getAuthor().getAccount().getUsername() )+ "/" + call.getProtocol();

            fileService.delete( path );
            module.getCallService().delete( call );
            json.appendField( "message", "Removido com sucesso!" );
            status = HttpStatus.OK;
        }
        catch( IOException | RuntimeException ex ) {
            json.appendField( "message", "Falha na exclusão!" );
            LOG.error( "CallController fails to delete: {}", ex.getMessage() );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>( json, status );
    }

    private String extractLoginFromContext() throws AuthenticationException {

        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null )
            throw new AuthenticationException( "no user authentication found" );

        var jwt = ( Jwt ) authToken.getPrincipal();
        return jwt.getClaim( "sub" ).toString();
    }

    private ResponseEntity<JSONObject> getInternalErrorMessage() {

        return ResponseEntity
                .status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( new JSONObject()
                        .appendField( "message","no user authentication found" ) );
    }

    private ResponseEntity<JSONObject> getSuccessResponse( Object content ) {

        var json = new JSONObject();
        json.appendField("result", content );

        return new ResponseEntity<>( json, HttpStatus.ACCEPTED );
    }
}
