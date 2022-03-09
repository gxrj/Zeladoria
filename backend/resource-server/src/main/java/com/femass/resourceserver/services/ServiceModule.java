package com.femass.resourceserver.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor

@Component
public class ServiceModule {

    @Autowired
    private AgentService agentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CallService callService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private UserFeedbackService userFeedbackService;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
}