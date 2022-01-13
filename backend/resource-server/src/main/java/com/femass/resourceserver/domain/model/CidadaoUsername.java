package com.femass.resourceserver.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import com.femass.resourceserver.domain.interfaces.Username;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@EqualsAndHashCode

@Builder
@Embeddable
public class CidadaoUsername implements Username {
    
    @Column( name = "email", unique = true, nullable = false, length = 50 )
    private String email;
}