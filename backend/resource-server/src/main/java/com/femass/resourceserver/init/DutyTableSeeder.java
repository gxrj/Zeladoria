package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.services.DepartmentService;
import com.femass.resourceserver.services.DutyService;
import org.springframework.util.Assert;

public class DutyTableSeeder {

    public static void seed( DutyService dutyService,
                             DepartmentService deptService ) throws RuntimeException {

        if( dutyService.countDuties() == 0 ) {

            var dept = deptService.findDepartmentByName( "Infraestrutura" );
            Assert.notNull( dept, "Department not found" );

            var duty = new Duty();
            duty.setDescription( "Bueiro sem tampa" );

            if( !dutyService.createOrUpdate( duty ) )
                throw new RuntimeException( "DutyService failed" );
        }
    }
}