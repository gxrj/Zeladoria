package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "Secretaria" )
public class Department implements Serializable {

    public Department( String name ) {
        this();
        this.name = name;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Long id;

    @NotNull
    @Column( name = "nome", nullable = false, unique = true, length = 100 )
    private String name;

    @Override
    public String toString() {
        return name;
    }
}