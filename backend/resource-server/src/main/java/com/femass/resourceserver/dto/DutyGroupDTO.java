package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.DutyGroup;
import com.femass.resourceserver.services.ServiceModule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;

@Getter @Setter
@NoArgsConstructor

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
        value = { "id" },
        allowSetters = true,
        ignoreUnknown = true
)
public class DutyGroupDTO implements Serializable {

    private Long id;
    private @NotNull String name;

    public DutyGroupDTO( String name ) { this.name = name; }

    @JsonValue
    public static DutyGroupDTO serialize( DutyGroup category ) {

        if( category == null ) return null;

        var dto = new DutyGroupDTO();
        dto.setName( category.getName() );

        return dto;
    }

    public static DutyGroup deserialize( DutyGroupDTO dto, ServiceModule module ) {

        var categoryService = module.getDutyGroupService();
        var category = categoryService.findDutyGroupByName( dto.name );

        if( category == null ) category = new DutyGroup();

        category.setName( dto.name );

        return category;
    }
}