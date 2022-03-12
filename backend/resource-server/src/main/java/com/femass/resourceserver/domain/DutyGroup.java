package com.femass.resourceserver.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter

@AllArgsConstructor @NoArgsConstructor

@Entity( name = "GrupoServico" )
public class DutyGroup implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "categoria", nullable = false, unique = true, length = 80 )
    private String name;

    @OneToMany( mappedBy = "dutyGroup" )
    private List<Duty> duties;
}