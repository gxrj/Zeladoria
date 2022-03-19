package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.AgentAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor

@AttributeOverride(
    name = "name",
    column = @Column( name = "matricula", nullable = false, unique = true, length = 10 ) )

@Entity( name = "Colaborador" )
public class Agent extends AbstractUser<AgentAccount> {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @ManyToOne
    @JoinColumn( name = "secretaria", referencedColumnName = "nome" )
    private Department department;

    public Agent( String name, AgentAccount account ) {
        this.name = name;
        this.account = account;
    }

    @Access( AccessType.PROPERTY )
    @OneToOne( targetEntity = AgentAccount.class )
    @JoinColumn( name = "conta", referencedColumnName = "matricula" )
    public AgentAccount getAccount(){ return account; }

    public void setAccount( AgentAccount account ) { this.account = account; }
}