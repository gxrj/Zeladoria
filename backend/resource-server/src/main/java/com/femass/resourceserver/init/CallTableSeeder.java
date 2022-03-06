package com.femass.resourceserver.init;

import com.femass.resourceserver.services.CallService;
import com.femass.resourceserver.services.DutyService;

public class CallTableSeeder {

    public static void seed( CallService callService,
                             DutyService dutyService ) throws RuntimeException {

        if( callService.countCalls() == 0 ) {

        }
    }
}