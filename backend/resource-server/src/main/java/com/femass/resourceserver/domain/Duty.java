package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString

@Entity( name = "Servico" )
public class Duty implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @NotNull
    @Column( name = "descricao", nullable = false, unique = true )
    private String description;

    @ManyToOne
    @JoinColumn( name = "secretaria", referencedColumnName = "nome" )
    private Department department;

    @ManyToOne
    @JoinColumn( name = "categoria", referencedColumnName = "categoria" )
    private DutyGroup dutyGroup;
}