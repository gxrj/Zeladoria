package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Entity( name = "Bairro" )
public class District implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Long id;

    @Column( name = "bairro", nullable = false, unique = true )
    private String name;

    public District( String name ) { this.name = name; }

    @Override
    public String toString() { return name; }
}