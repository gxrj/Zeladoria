package com.femass.resourceserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class Address {

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