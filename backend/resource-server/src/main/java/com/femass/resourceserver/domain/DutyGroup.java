package com.femass.resourceserver.domain;

import java.io.Serializable;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "GrupoServico" )
public class DutyGroup implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Long id;

    @Column( name = "categoria", nullable = false, unique = true, length = 80 )
    private String name;

}