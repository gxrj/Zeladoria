package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.Department;
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
public class DepartmentDTO implements Serializable {

    private UUID id;
    private @NotNull String name;

    @JsonValue
    public static String serialize( Department dept ) {
        return dept.toString();
    }

    public static Department deserialize( DepartmentDTO deptDto, ServiceModule module ) {

        var dept = module.getDepartmentService().findDepartmentByName( deptDto.name );

        if( dept == null ){
            dept = new Department();
        }
        dept.setName( deptDto.name );

        return dept;
    }
}