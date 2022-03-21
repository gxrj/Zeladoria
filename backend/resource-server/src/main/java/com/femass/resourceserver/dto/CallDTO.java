package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.domain.Citizen;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.domain.account.CitizenAccount;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@ToString

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )
public class CallDTO implements Serializable {

    private UUID id;
    private DutyDTO duty;
    private String protocol;
    private Status status;
    private String description;
    private Address address;
    private List<String> images;

    @NotNull
    private CitizenDTO author;
    private Timestamp createdAt;

    @JsonValue
    public static CallDTO serialize( Call call ) {

        if( call == null ) return null;

        var callDto = new CallDTO();
        callDto.setDuty( DutyDTO.serialize( call.getDuty() ) );
        callDto.setProtocol( call.getProtocol() );
        callDto.setStatus(  call.getStatus() );
        callDto.setDescription( call.getDescription() );
        callDto.setAddress( call.getAddress() );
        callDto.setImages( call.getImages() );
        callDto.setAuthor( CitizenDTO.serialize( call.getAuthor() ) );
        callDto.setCreatedAt( call.getPostingDate() );

        return callDto;
    }

    public static Call deserialize( CallDTO callDto, ServiceModule module ) {

        var callService = module.getCallService();

        var object = callDto.protocol != null ?
                                    callService.findCallByProtocol( callDto.protocol ) : null;

        var email = callDto.author.getEmail();

        if( object == null ) {

            var citizenService = module.getCitizenService();
            var protocol = String.format( "%d%H", System.currentTimeMillis(), email );

            var author = citizenService.findByUsername( email );

            if( author == null )
                author = citizenService.findByUsername( "anonimo@fiscaliza.com" );

            object = new Call(); /* The attributes bellow cannot change after first creation */
            object.setAuthor( author );
            object.setProtocol( protocol );
            object.setPostingDate( new Timestamp( System.currentTimeMillis() ) );
        }

        var dutyService = module.getDutyService();

        if( callDto.duty != null )
            object.setDuty( dutyService.findDutyByDescription( callDto.duty.getDescription() ) );
        if( callDto.status != null )
            object.setStatus( callDto.status );
        if( callDto.description != null )
            object.setDescription( callDto.description );
        if( callDto.address != null )
            object.setAddress( callDto.address );
        if( callDto.images != null && !callDto.images.isEmpty() )
            object.setImages( callDto.getImages() );

        return object;
    }
}