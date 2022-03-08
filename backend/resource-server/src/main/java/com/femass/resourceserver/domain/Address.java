package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@JsonRootName( value = "address" )
@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )

@Embeddable
public class Address implements Serializable {

    @Column( name = "latitude" )
    private Double latitude;
    @Column( name = "longitude" )
    private Double longitude;
    @Column( name = "cep", length = 8 )
    private String zipCode;
    @Column( name = "logradouro" )
    private String publicPlace;
    @Column( name = "bairro" )
    private String district;
    @Column( name = "referencia", length = 80 )
    private String reference;
}