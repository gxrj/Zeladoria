package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.account.CitizenAccount;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CitizenAccountTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var accountService = seeder.getServiceModule().getCitizenAccountService();
        var encoder = seeder.getEncoder();

        if( accountService.countCitizenAccounts() == 0 ) {

            var account = new CitizenAccount();
            account.setUsername( "user" );
            account.setCredentials( encoder.encode("123" ) );
            account.setAuthorities( List.of( new SimpleGrantedAuthority( "ROLE_USER" ) ) );

            var anonymous = new CitizenAccount();
            anonymous.setUsername( "anonimo@fiscaliza.com" );
            anonymous.setCredentials( encoder.encode("123" ) );
            anonymous.setAuthorities( List.of( new SimpleGrantedAuthority( "ROLE_ANONYMOUS" ) ) );

            if( !accountService.createMultiple( List.of( account, anonymous ) ) )
                throw new RuntimeException( "Citizen Account Seeder failed!" );
        }
    }
}