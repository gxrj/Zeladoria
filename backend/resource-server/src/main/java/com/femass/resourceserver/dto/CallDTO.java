package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import lombok.ToString;

import javax.validation.constraints.NotEmpty;
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
    private DepartmentDTO destination;

    @NotEmpty
    private CitizenDTO author;
    private Timestamp createdAt;

    @JsonValue
    public static CallDTO serialize( Call call ) {

        if( call == null ) return null;

        var callDto = new CallDTO();
        var duty = call.getDuty();
        callDto.setDuty( DutyDTO.serialize( duty ) );
        callDto.setProtocol( call.getProtocol() );
        callDto.setStatus(  call.getStatus() );
        callDto.setDescription( call.getDescription() );
        callDto.setAddress( call.getAddress() );
        callDto.setImages( call.getImages() );
        callDto.setAuthor( CitizenDTO.serialize( call.getAuthor() ) );
        callDto.setCreatedAt( call.getPostingDate() );
        callDto.setDestination( DepartmentDTO.serialize( duty.getDepartment() ) );

        return callDto;
    }

    public static Call deserialize( CallDTO callDto, ServiceModule module ) {

        var callService = module.getCallService();

        var object = callDto.protocol != null ?
                                    callService.findCallByProtocol( callDto.protocol ) : null;

        var objectIsStored = object != null;
        var email = callDto.author.getEmail();

        if( !objectIsStored ) {

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

        if( callDto.duty != null ) {
            var duty = dutyService.findDutyByDescription(
                                        callDto.duty.getDescription() );
            object.setDuty( duty );

            if( !objectIsStored ) object.setDestination( duty.getDepartment() );
        }

        if( callDto.status != null )
            object.setStatus( callDto.status );

        if( callDto.description != null )
            object.setDescription( callDto.description );

        if( callDto.images != null && !callDto.images.isEmpty() )
            object.setImages( callDto.getImages() );

        if( objectIsStored && callDto.destination != null )
            object.setDestination( module.getDepartmentService()
                                        .findDepartmentByName( callDto.destination.getName() ) );

        if( callDto.address != null ) {

            var district = callDto.address.getDistrict();

            if( district != null )
                callDto.address.setDistrict(
                    module.getDistrictService().findDistrictByName( district.getName() ) );

            object.setAddress( callDto.address );
        }

        return object;
    }
}