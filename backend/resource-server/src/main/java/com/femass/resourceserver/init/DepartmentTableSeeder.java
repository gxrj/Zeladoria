package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Department;

public class DepartmentTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var deptService = seeder.getServiceModule().getDepartmentService();

        if( deptService.countDepartments() == 0 ) {

            var dept = new Department( "Infraestrutura" );

            if( !deptService.createOrUpdate( dept ) )
                throw new RuntimeException( "Department seeder failed" );
        }
    }
}