package com.femass.resourceserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity( name = "Servico" )
public class Duty {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "descricao" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "id_secretaria" )
    private Department department;
}