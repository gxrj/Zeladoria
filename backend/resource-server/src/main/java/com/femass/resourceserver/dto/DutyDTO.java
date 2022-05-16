package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id" },
    allowSetters = true,
    ignoreUnknown = true
)
public class DutyDTO implements Serializable {

    private Long id;
    private @NotEmpty String description;
    private @NotEmpty DepartmentDTO department;
    private DutyGroupDTO category;

    @JsonValue
    public static DutyDTO serialize( Duty duty ) {

        if( duty == null ) return null;

        var dutyDto = new DutyDTO();
        dutyDto.setDescription( duty.getDescription() );
        dutyDto.setDepartment( DepartmentDTO.serialize( duty.getDepartment() ) );
        dutyDto.setCategory( DutyGroupDTO.serialize( duty.getDutyGroup() ) );

        return dutyDto;
    }

    public static Duty deserialize( DutyDTO dutyDto, ServiceModule module ) {

        var dutyService = module.getDutyService();
        var duty = dutyService.findDutyByDescription( dutyDto.description );

        if( duty == null ) {
            duty = new Duty();
            duty.setDescription( dutyDto.description );
        }

        duty.setDepartment( DepartmentDTO.deserialize( dutyDto.department, module ) );

        if( dutyDto.category != null )
            duty.setDutyGroup( DutyGroupDTO.deserialize( dutyDto.category, module ) );

        return duty;
    }
}