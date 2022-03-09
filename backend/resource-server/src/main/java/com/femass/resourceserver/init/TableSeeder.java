package com.femass.resourceserver.init;

import com.femass.resourceserver.services.*;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter

@Component
public class TableSeeder implements CommandLineRunner {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ServiceModule serviceModule;

    @Override
    public void run( String... args ) throws RuntimeException {

        AgentEntityTableSeeder.seed( this );
        UserEntityTableSeeder.seed( this );
        DepartmentTableSeeder.seed( this );
        DutyTableSeeder.seed( this );
        CallTableSeeder.seed( this );
//        AttendanceTableSeeder.seed( this );
    }
}