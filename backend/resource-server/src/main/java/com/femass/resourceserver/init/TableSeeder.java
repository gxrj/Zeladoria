package com.femass.resourceserver.init;

import com.femass.resourceserver.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TableSeeder implements CommandLineRunner {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService deptService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private CallService callService;

    @Autowired
    private AttendanceService attendanceService;

    @Override
    public void run( String... args ) throws RuntimeException {

        AgentEntityTableSeeder.seed( agentService, encoder );
        UserEntityTableSeeder.seed( userService, encoder );
        DepartmentTableSeeder.seed( deptService );
        DutyTableSeeder.seed( dutyService, deptService );
        CallTableSeeder.seed( callService, dutyService );
//        AttendanceTableSeeder.seed( attendanceService );
    }
}