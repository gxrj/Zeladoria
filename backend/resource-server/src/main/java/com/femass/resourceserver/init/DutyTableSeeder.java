package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Duty;

import org.springframework.util.Assert;

public class DutyTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var dutyService = module.getDutyService();

        if( dutyService.countDuties() == 0 ) {

            var dept = module.getDepartmentService().findDepartmentByName( "Infraestrutura" );
            Assert.notNull( dept, "Department not found while seeding" );

            var category = module.getDutyGroupService()
                                        .findDutyGroupByName( "Agua Pluvial, Bueiros e Esgoto" );

            Assert.notNull( category, "DutyGroup not found while seeding" );

            var duty = new Duty();
            duty.setDescription( "Bueiro sem tampa" );
            duty.setDepartment( dept );
            duty.setDutyGroup( category );

            if( !dutyService.createOrUpdate( duty ) )
                throw new RuntimeException( "Duty seeder failed" );
        }
    }
}