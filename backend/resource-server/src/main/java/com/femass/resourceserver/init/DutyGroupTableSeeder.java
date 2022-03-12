package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.DutyGroup;

import java.util.Collections;

public class DutyGroupTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var categoryService = module.getDutyGroupService();

        if( categoryService.countDutyGroups() == 0 ) {
            var category = new DutyGroup();
            category.setName( "Agua Pluvial, Bueiros e Esgoto" );
            category.setDuties( Collections.emptyList() );

            if( !categoryService.createOrUpdate( category ) )
                throw new RuntimeException( "DutyGroup seeder failed!" );
        }
    }
}