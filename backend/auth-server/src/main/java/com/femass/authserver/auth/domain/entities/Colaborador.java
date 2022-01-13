package com.femass.authserver.auth.domain.entities;

import com.femass.authserver.auth.domain.abstracts.Conta;
import com.femass.authserver.auth.domain.model.ColaboradorUsername;
import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity( name = "Colaborador" )
public class Colaborador extends Conta implements Serializable {
    
    @Embedded
    private ColaboradorUsername username;

    public Colaborador( String cpf, String matricula, String nome, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.username = ColaboradorUsername.builder()
                                                .cpf( cpf )
                                                .matricula( matricula )
                                                .build();
    }

    @Override
    public ColaboradorUsername getUsername(){ return this.username; }
}