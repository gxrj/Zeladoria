package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.CitizenAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@ToString

@AttributeOverride(
    name = "name",
    column = @Column( name = "email", nullable = false, unique = true ) )
@Entity( name = "Cidadao" )
public class Citizen extends AbstractUser {

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
    public CitizenAccount getAccount() { return ( CitizenAccount ) account; }

    public void setAccount( CitizenAccount account ) { this.account = account; }
}