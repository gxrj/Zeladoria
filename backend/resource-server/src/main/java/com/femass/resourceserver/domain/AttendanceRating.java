package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum AttendanceRating implements Serializable {
    NOT_RATED( "nao avaliada" ),
    POSITIVE( "positiva" ),
    NEGATIVE( "nagativa" );

    private final String value;

    AttendanceRating( String value ) { this.value = value; }

    @JsonValue
    @Override
    public String toString() { return this.value; }

    public boolean isEqualTo( AttendanceRating rating ) { return this.value.equalsIgnoreCase( rating.toString() );}
}