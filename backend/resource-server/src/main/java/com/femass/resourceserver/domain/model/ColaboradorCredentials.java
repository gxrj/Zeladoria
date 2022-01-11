package com.femass.resourceserver.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import com.femass.resourceserver.domain.interfaces.Login;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

@Builder
@Embeddable
public class ColaboradorCredentials implements Login {
    
    @Column( name = "cpf", unique = true, length = 11 )
    private String cpf;

    @Column( name = "matricula", unique = true, length = 20 )
    private String matricula;
}