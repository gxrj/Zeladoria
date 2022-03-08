package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.femass.resourceserver.domain.user.UserEntity;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor

@Entity( name = "Ocorrencia" )
public class Call implements Serializable {

    @Id
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    @ManyToOne
    @JoinColumn( name = "servico", referencedColumnName = "descricao" )
    private Duty duty;

    @Setter( AccessLevel.NONE )
    @Column( name = "protocolo", nullable = false, unique = true )
    private String protocol;

    @Column( name = "status" )
    private Status status;

    @Column( name = "descricao")
    private String description;

    @Embedded
    private Address address;

    @Embedded
    private List<String> images;

    @ManyToOne
    @JoinColumn( name = "usuario", referencedColumnName = "email" )
    private UserEntity author;

    @Column( name = "dt_postagem" )
    private Timestamp postingDate;

    @OneToMany( mappedBy = "userCall" )
    private List<Attendance> attendances;

    public Call() {
        //TODO: generate unique protocol for simultaneous user calls
        var time = System.currentTimeMillis();
        this.protocol = String.format( "%d%H", time, author.getUsername() );
    }

    @JsonValue
    public JSONObject toJson() {
        var json = new JSONObject();
        json.appendField( "duty", duty );
        json.appendField( "protocol", protocol );
        json.appendField( "status", status );
        json.appendField( "description", description );
        json.appendField( "address", address );
        json.appendField( "images", images );
        json.appendField( "author", author );
        json.appendField( "created_at", postingDate );

        return json;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}