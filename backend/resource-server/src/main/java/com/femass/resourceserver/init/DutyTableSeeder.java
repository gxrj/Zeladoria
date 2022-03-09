package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Duty;

import org.springframework.util.Assert;

public class DutyTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var dutyService = module.getDutyService();

        if( dutyService.countDuties() == 0 ) {

            var dept = module.getDepartmentService().findDepartmentByName( "Infraestrutura" );
            Assert.notNull( dept, "Department not found" );

            var duty = new Duty();
            duty.setDescription( "Bueiro sem tampa" );
            duty.setDepartment( dept );

            if( !dutyService.createOrUpdate( duty ) )
                throw new RuntimeException( "Duty seeder failed" );
        }
    }
}