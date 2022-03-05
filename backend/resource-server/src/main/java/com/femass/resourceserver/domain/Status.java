package com.femass.resourceserver.domain;

import lombok.Getter;

@Getter

public enum Status {

    PROCESSING( "Em andamento" ),
    FORWARDED( "Encaminhada" ),
    ANSWERED( "Respondida" ),
    REJECTED( "Indeferida" ),
    FINISHED( "Finalizada" );

    private final String value;

    Status( String value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean equals( String status ) {
        return this.value.equalsIgnoreCase( status );
    }
}