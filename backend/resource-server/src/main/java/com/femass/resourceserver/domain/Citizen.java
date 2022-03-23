package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.CitizenAccount;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder

@Entity( name = "Cidadao" )
public class Citizen extends AbstractUser<CitizenAccount> {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    public Citizen( String name, CitizenAccount account ) {
        this.name = name;
        this.account = account;
    }

    @Access( AccessType.PROPERTY )
    @OneToOne( targetEntity = CitizenAccount.class )
    @JoinColumn( name = "conta", referencedColumnName = "email" )
    public CitizenAccount getAccount() { return account; }

    public void setAccount( CitizenAccount account ) { this.account = account; }

    @Access( AccessType.PROPERTY )
    @Column( name = "nome", length = 50 )
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }
}