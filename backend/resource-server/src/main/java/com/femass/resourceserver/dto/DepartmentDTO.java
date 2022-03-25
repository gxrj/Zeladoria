package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Department;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id" },
    allowSetters = true,
    ignoreUnknown = true
)
public class DepartmentDTO implements Serializable {

    private Long id;
    private @NotEmpty String name;

    /**
     *
     * This constructor allows json input be like:
     * <pre>
     * {
     *     "department": "Infrastructure"
     * }
     * </pre>
     * instead of only allow:
     * <pre>
     * {
     *     "department": {
     *         "name" : "Infrastructure"
     *     }
     * }
     * </pre>
     * */
    public DepartmentDTO( String name ) { this.name = name; }

    @JsonValue
    public static DepartmentDTO serialize( Department dept ) {

        if( dept == null ) return null;

        var deptDto = new DepartmentDTO();
        deptDto.name = dept.getName();

        return deptDto;
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