package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Department;
import com.femass.resourceserver.services.DepartmentService;

public class DepartmentTableSeeder {

    public static void seed( DepartmentService deptService ) {

        if( deptService.countDepartments() == 0 ) {
            var dept = new Department( "Infraestrutura" );
            if( !deptService.createOrUpdate( dept ) ) throw new RuntimeException( "Department Service failed" );
        }
    }
}