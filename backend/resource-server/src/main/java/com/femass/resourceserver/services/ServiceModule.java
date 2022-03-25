package com.femass.resourceserver.services;

import com.femass.resourceserver.services.accountservices.AgentAccountService;
import com.femass.resourceserver.services.accountservices.CitizenAccountService;
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
    private AgentAccountService agentAccountService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CallService callService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private CitizenAccountService citizenAccountService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private DutyGroupService dutyGroupService;
    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private PasswordEncoder passwordEncoder;
}