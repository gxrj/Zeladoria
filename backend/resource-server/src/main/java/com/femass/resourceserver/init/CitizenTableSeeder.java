package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Citizen;

import java.util.List;

public class CitizenTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var citizenService = seeder.getServiceModule().getCitizenService();
        var accountService = seeder.getServiceModule().getCitizenAccountService();

        if( citizenService.countUsers() == 0 ) {

            var account = accountService.findByUsername( "user" );
            var citizen = new Citizen();
            citizen.setName( "usuario" );
            citizen.setAccount( account );

            var anonymous = new Citizen();
            anonymous.setName( "anonimo" );
            anonymous.setAccount( accountService.findByUsername( "anonimo@fiscaliza.com" ) );

            if ( !citizenService.createMultiple( List.of( anonymous, citizen ) ) )
                throw new RuntimeException( "CitizenTable seeder failed" );
        }
    }
}