package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.services.DepartmentService;
import com.femass.resourceserver.services.DutyService;
import org.springframework.util.Assert;

public class DutyTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var dutyService = seeder.getDutyService();

        if( dutyService.countDuties() == 0 ) {

            var dept = seeder.getDeptService().findDepartmentByName( "Infraestrutura" );
            Assert.notNull( dept, "Department not found" );

            var duty = new Duty();
            duty.setDescription( "Bueiro sem tampa" );
            duty.setDepartment( dept );

            if( !dutyService.createOrUpdate( duty ) )
                throw new RuntimeException( "Duty seeder failed" );
        }
    }
}