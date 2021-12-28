package com.femass.resourceserver.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.femass.resourceserver.domain.trait.Usuario;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public class Conta implements Usuario {
    
    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @Column( name = "nome", nullable = false )
    protected String nome;

    @Column( name = "senha", nullable = false )
    protected String senha;
}
