package com.femass.resourceserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.dto.AgentDTO;
import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.services.FileStorageService;
import com.femass.resourceserver.services.ServiceModule;

import com.nimbusds.jose.shaded.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import java.io.IOException;

import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = "application/json;charset=utf-8"
)
public class CallController {

    @Autowired
    private ServiceModule module;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private FileStorageService fileService;

    private final Logger LOG = LoggerFactory.getLogger( CallController.class );

    @PostMapping( "/authenticated/call/edition" )
    public ResponseEntity<JSONObject> updateCall( @RequestBody CallDTO callDto ) {

        var result = persistEntity(
                                CallDTO.deserialize( callDto, module ), null );
        callDto = result ? callDto : null;

        return prepareResponse( callDto, "call",
                "Ocorrência gravada com sucesso!",
                 "Falha na gravação da ocorrência!" );
    }

    @PostMapping( "/anonymous/calls/new" )
    public ResponseEntity<JSONObject> create(
            @RequestParam( name = "call" ) String plainCall,
            @RequestParam( name = "files", required = false ) MultipartFile[] images ) {

        var entity = fromPlainToEntity( plainCall );
        var result = persistEntity( entity, images );
        plainCall = result ? plainCall : null;

        return prepareResponse( plainCall, "call",
                "Ocorrência gravada com sucesso!",
                "Falha na gravação da ocorrência!" );
    }

    private Call fromPlainToEntity( String plainCall ) {
        try{
            var callDto = mapper.readValue( plainCall, CallDTO.class );
            return CallDTO.deserialize( callDto, module );
        }
        catch ( JsonProcessingException ex ) {
            LOG.error( "Error parsing entity from plain text: {}", ex.getMessage() );
            return null;
        }
    }

    private boolean persistEntity( Call entity, MultipartFile[] images ) {

        var persisted = module.getCallService().createOrUpdate( entity );

        /* Upload images only if they exist in request and call was persisted */
        if( images != null && images.length > 0 && persisted ) {
            var username = sanitizeUsername( entity.getAuthor().getAccount().getUsername() );
            Arrays.stream( images ).parallel()
                    .forEach( image -> fileService.store( image, username, entity.getProtocol() ) );
        }

        return persisted;
    }

    private String sanitizeUsername( String email ) {
        email = email.equalsIgnoreCase("anonimo@fiscaliza.com" )? "anonimo" : email;
        return email.replaceAll( "[^\\w]", "_" );
    }

    @PostMapping(
        path = "/authenticated/call/file?{filename}",
        consumes =  MediaType.APPLICATION_JSON_VALUE,
        produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE  } )
    public @ResponseBody byte[] downloadFile(
            @RequestBody CallDTO callDto, @PathParam( "filename" ) String filename ) {

        var filePath = sanitizeUsername( callDto.getAuthor().getEmail() ) +
                                    "/"+ callDto.getProtocol() +"/"+ filename;
        try {
            var resource = fileService.loadAsResource( filePath );
            return FileCopyUtils.copyToByteArray( resource.getInputStream() );
        }
        catch ( IOException ex ) {
            LOG.error( "Erro, arquivo não encontrado ou inexistente: {}", ex.getMessage() );
            return null;
        }
    }

    @PostMapping(
        path = "/authenticated/call/files/zip",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void downloadFilesZipped( @RequestBody CallDTO callDto, HttpServletResponse response ) {

        var filePath = sanitizeUsername( callDto.getAuthor().getEmail() ) +"/"+ callDto.getProtocol();

        var resources = fileService
                        .loadAllFilesFromDirectory( filePath );

        if( resources == null || resources.isEmpty() ) return;

        try ( var zip = new ZipOutputStream( response.getOutputStream() ) ) {
            for( var el : resources ) {
                var filename = el.getFile().getName();
                var entry = new ZipEntry( filename );

                entry.setSize( el.contentLength() );
                zip.putNextEntry( entry );
                StreamUtils.copy( el.getInputStream(), zip );
                zip.closeEntry();
            }
            zip.finish();
            response.setContentType( MediaType.APPLICATION_OCTET_STREAM_VALUE );
            response.setContentType( "application/zip" );
            response.flushBuffer();
        }
        catch( IOException ex ) {
            LOG.error( "Error at file transmission: {}", ex.getMessage() );
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
        }
    }

    @PostMapping( "/agent/calls/all" )
    public ResponseEntity<JSONObject> listCallsByAgentDeptartment( @RequestBody AgentDTO agent ) {

        var calls = module.getCallService()
                .findCallByDestination( agent.getDepartment().getName() )
                .parallelStream().map( CallDTO::serialize ).toList();

        return prepareResponse( calls, "result", "",
                   "Falha no carregamento de ocorrências!" );
    }

    @GetMapping( "/user/calls/all" )
    public ResponseEntity<JSONObject> listAllByLoggedUser() {

        List<CallDTO> calls = null;
        var login = extractLoginFromContext();

        if( login != null )
            calls = module.getCallService()
                    .findCallByAuthor( login )
                    .parallelStream().map( CallDTO::serialize ).toList();

        return prepareResponse( calls, "result", "",
                  "Falha no carregamento de ocorrências" );
    }

    @GetMapping( "/agent/calls/all" )
    public ResponseEntity<JSONObject> getCallsByAgentDeptartment() {

        List<CallDTO> calls = null;
        var login = extractLoginFromContext();

        if( login != null ) {
           var agent = module.getAgentService().findByUsername( login );

           calls = module.getCallService()
                   .findCallByDestination( agent.getDepartment().getName() )
                   .parallelStream().map( CallDTO::serialize ).toList();
        }

        return prepareResponse( calls, "result", "",
                  "Falha no carregamento de ocorrências!" );
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

    private String extractLoginFromContext() {

        var authToken = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();

        if( authToken == null ) return null;

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

    private ResponseEntity<JSONObject> prepareResponse(
            Object entity, String entityKey, String successMessage, String failureMessage ) {

        var json = new JSONObject();
        HttpStatus status;
        if( entity != null ) {
            json.appendField( entityKey, entity )
                    .appendField( "message", successMessage );
            status = HttpStatus.CREATED;
        }
        else {
            json.appendField( "message", failureMessage );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>( json, status );
    }
}
