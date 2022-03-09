package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;

import com.femass.resourceserver.domain.Status;
import org.springframework.util.Assert;

import java.sql.Timestamp;

public class CallTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();

        if( module.getCallService().countCalls() == 0 ) {
            var duty = module.getDutyService().findDutyByDescription( "Bueiro sem tampa" );
            Assert.notNull( duty, "Duty not found" );

            var user = module.getUserService().findByUsername( "user" );
            Assert.notNull( user, "User not found" );

            var address = new Address();
            address.setLatitude( -41.7828 );
            address.setLongitude( -22.3837 );
            address.setZipCode( "27900000" );
            address.setDistrict( "downtown" );

            var protocol = String.format( "%d%H", System.currentTimeMillis(), user.getUsername() );

            var call = new Call();
            call.setAuthor( user );
            call.setProtocol( protocol );
            call.setDuty( duty );
            call.setAddress( address );
            call.setPostingDate( new Timestamp( System.currentTimeMillis() ) );
            call.setStatus( Status.PROCESSING );
            call.setDescription( "Bueiro destruido pelas chuvas no centro" );

            if( !module.getCallService().createOrUpdate( call ) )
                throw new RuntimeException( "CallTable seeder failed" );
        }
    }
}