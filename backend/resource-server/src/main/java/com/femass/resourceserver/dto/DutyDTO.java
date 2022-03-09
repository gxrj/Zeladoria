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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id" },
    allowSetters = true,
    ignoreUnknown = true
)
public class DutyDTO implements Serializable {

    private UUID id;
    private @NotNull String description;
    private @NotNull DepartmentDTO department;

    @JsonValue
    public static DutyDTO serialize( Duty duty ){
        var dutyDto = new DutyDTO();
        dutyDto.setDescription( duty.getDescription() );
        dutyDto.setDepartment( DepartmentDTO.serialize( duty.getDepartment() ) );

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

        return duty;
    }
}