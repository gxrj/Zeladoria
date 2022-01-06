package com.femass.authserver.auth.domain.abstracts;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

@MappedSuperclass
public abstract class Conta {
    
    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @Column( name = "nome", nullable = false )
    protected String nome;

    @Column( name = "senha", nullable = false )
    protected String senha;

    @Column( name = "habilitada", nullable = false )
    protected Boolean contaAtivada = true;

    protected Conta(){}

    protected Conta( String nome, String senha, Boolean contaAtivada ){
        this.nome = nome;
        this.senha = senha;
        this.contaAtivada = contaAtivada;
    }

    public String getSenha() { return this.senha; }
    public Boolean getContaAtivada() { return this.contaAtivada; }

    public abstract String getLogin();
    public abstract String getCpf() throws NoSuchFieldException;
}