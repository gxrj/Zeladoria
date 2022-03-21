package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder

@JsonRootName( value = "address" )
@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

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
    @ManyToOne
    @JoinColumn( name = "bairro" )
    private District district;
    @Column( name = "referencia", length = 80 )
    private String reference;
}