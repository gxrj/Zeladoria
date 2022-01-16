package com.femass.authserver.auth.filters;


import java.io.IOException;

import javax.servlet.ServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.femass.authserver.auth.domain.model.CidadaoAuthToken;
import com.femass.authserver.auth.domain.model.ColaboradorAuthToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import org.springframework.stereotype.Component;

@Component
public class UserAuthFilter extends AuthenticationFilter {

    @Autowired
    private static AuthenticationManager authManager;

    public UserAuthFilter(){

        super( authManager, setAuthenticationConverter() );
    }

    private static AuthenticationConverter setAuthenticationConverter(){

        /** Returns AuthenticationConverter as a lamda expression */
        return ( request ) -> {
            try{
                var json = parseIntoJsonNode( request );

                return delegateAuth( json );
            }
            catch( Exception ex ){ return null; }
        };     
    }

    public static JsonNode parseIntoJsonNode ( ServletRequest req ) throws IOException{
        var requestBody = req.getReader();
        var objMapper = Jackson2ObjectMapperBuilder.json().build();
        return objMapper.readTree( requestBody );
    }

    public static Authentication delegateAuth( JsonNode json ){

        var password = json.get( "senha" ).asText();

        if( password == null ) return null;

        var cpf = json.get( "cpf" ).asText();
        var email = json.get( "email" ).asText();
        var matricula = json.get( "matricula" ).asText();
        
        if( email != null ) 
            return new CidadaoAuthToken( email, password );

        if( matricula != null && cpf != null ) 
            return new ColaboradorAuthToken( matricula, password, cpf );

        return null;
    }
}