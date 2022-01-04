package com.femass.resourceserver.domain.model;

import com.femass.resourceserver.domain.abstracts.Conta;
import com.femass.resourceserver.domain.entity.Cidadao;
import com.femass.resourceserver.domain.entity.Colaborador;
import com.femass.resourceserver.domain.enums.TipoUsuario;

import org.springframework.util.Assert;


public final class UsuarioBuilder {
    
    private String nome;
    private String email;
    private String senha;
    private Boolean contaHabilidata = true;
    private String cpf;
    private String matricula;
    private TipoUsuario tipo;

    public UsuarioBuilder(){}

    public UsuarioBuilder setNome( String nome ){ 
        Assert.notNull( nome , "nome cannot be null" );
        this.nome = nome;
        return this;
    }
    public UsuarioBuilder setEmail( String email ){ 
        this.email = email;
        return this;
    }
    public UsuarioBuilder setSenha( String senha ){ 
        Assert.notNull( senha , "senha cannot be null" );
        this.senha = senha;
        return this;
    }
    public UsuarioBuilder setAtivacao( Boolean ativacao ){ 
        this.contaHabilidata = ativacao;
        return this;
    }
    public UsuarioBuilder setTipo( TipoUsuario tipo ){
        Assert.notNull( tipo , "tipo cannot be null" );
        this.tipo = tipo;
        return this;
    }
    public UsuarioBuilder setCpf( String cpf ){
        this.cpf = cpf;
        return this;
    }
    public UsuarioBuilder setMatricula( String matricula ){
        this.matricula = matricula;
        return this;
    }

    public Conta build() throws Exception {
        Conta result;
        switch( this.tipo ){
            case Cidadao ->  result =  new Cidadao( nome, email, senha, contaHabilidata ); 
            case Colaborador -> result = new Colaborador( cpf, matricula, nome, senha, contaHabilidata );
            default -> throw new Exception( "TipoUsuario inexistente!" );
        }
        return result;
    }
}