package com.femass.resourceserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter

@AllArgsConstructor @NoArgsConstructor

@Entity( name = "Secretaria" )
public class Department implements Serializable {

    public Department( String name ) {
        this();
        this.name = name;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @NotNull
    @Column( name = "nome", nullable = false, unique = true, length = 100 )
    private String name;

    @Override
    public String toString() {
        return name;
    }
}