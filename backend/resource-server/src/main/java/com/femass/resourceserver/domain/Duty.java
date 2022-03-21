package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "Servico" )
public class Duty implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Long id;

    @NotNull
    @Column( name = "descricao", nullable = false, unique = true )
    private String description;

    @ManyToOne
    @JoinColumn( name = "secretaria" )
    private Department department;

    @ManyToOne
    @JoinColumn( name = "categoria" )
    private DutyGroup dutyGroup;
}