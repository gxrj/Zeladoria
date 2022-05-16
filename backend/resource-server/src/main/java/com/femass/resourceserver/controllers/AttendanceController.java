package com.femass.resourceserver.controllers;

import com.femass.resourceserver.dto.AgentDTO;
import com.femass.resourceserver.dto.AttendanceDTO;
import com.femass.resourceserver.dto.CallDTO;
import com.femass.resourceserver.dto.DepartmentDTO;
import com.femass.resourceserver.services.ServiceModule;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class AttendanceController {

    @Autowired
    private ServiceModule module;

    @PostMapping( path = "/authenticated/attendance/by_call" )
    public ResponseEntity<JSONObject> getAttendanceListByCall( @RequestBody CallDTO callDTO ) {

        var json = new JSONObject();

        var attendances = module.getAttendanceService()
                .findAttendanceByCallProtocol( callDTO.getProtocol() )
                .parallelStream().map( AttendanceDTO::serialize ).toList();

        json.appendField( "result", attendances );

        return ResponseEntity.ok( json );
    }

    @PostMapping( path = "/authenticated/attendance/edition" )
    public ResponseEntity<JSONObject> attendanceUpdate( @RequestBody AttendanceDTO attendance ) {
        return this.createAttendance( attendance );
    }

    @PostMapping( path = "/agent/attendance/new" )
    public ResponseEntity<JSONObject> createAttendance( @RequestBody AttendanceDTO dto ) {

        var attendance = AttendanceDTO.deserialize( dto, module );
        var json = new JSONObject();
        HttpStatus status;

        if( module.getAttendanceService().createOrUpdate( attendance ) ) {
            json.appendField( "message", "Atendimento gravado com sucesso!" );
            status = HttpStatus.ACCEPTED;
        }
        else {
            json.appendField( "message", "Falha na gravação do atendimento!" );
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status( status ).body( json );
    }

    @PostMapping( path = "/agent/attendance/by_department" )
    public ResponseEntity<JSONObject> getAttendanceListByDepartment( @RequestBody DepartmentDTO deptDto ) {

        var attendances = module.getAttendanceService()
                                                .findAttendanceByDepartment( deptDto.getName() )
                                                .parallelStream().map( AttendanceDTO::serialize ).toList();

        var json = new JSONObject();
        json.appendField( "result", attendances );

        return ResponseEntity.ok( json );
    }

    @PostMapping( path = "/agent/attendance/by_agent" )
    public ResponseEntity<JSONObject> getAttendanceListByAgent( @RequestBody AgentDTO agentDto ) {

        var attendances = module.getAttendanceService()
                .findAttendanceByAgent( agentDto.getUsername() )
                .parallelStream().map( AttendanceDTO::serialize ).toList();

        var json = new JSONObject();
        json.appendField( "result", attendances );

        return ResponseEntity.ok( json );
    }

    @PostMapping( path = "/agent/attendance/by_rating" )
    public ResponseEntity<JSONObject> getAttendanceByRatingForAgents(
            @RequestBody AgentDTO agentDto,
            @RequestParam( required = false ) String rating ) {

        var dept = DepartmentDTO.deserialize( agentDto.getDepartment(), module );
        var attendances = module.getAttendanceService()
                .findAttendanceByRating( rating.toLowerCase(), dept )
                .parallelStream().map( AttendanceDTO::serialize ).toList();

        var json = new JSONObject();
        json.appendField( "result", attendances );

        return ResponseEntity.ok( json );

    }

    @GetMapping( path = "/manager/attendance/all" )
    public ResponseEntity<JSONObject> getAll( ) {

        var json = new JSONObject();

        var attendances = module.getAttendanceService().findAll()
                                                    .parallelStream().map( AttendanceDTO::serialize )
                                                    .toList();

        json.appendField( "result", attendances );
        return ResponseEntity.ok( json );
    }

    @PostMapping( path = "/manager/attendance/by_rating" )
    public ResponseEntity<JSONObject> getAttendanceByRatingForAdmins(
            @RequestParam( required = false ) String rating ) {

        var attendances = module.getAttendanceService()
                .findAttendanceByRating( rating.toLowerCase(), null )
                .parallelStream().map( AttendanceDTO::serialize ).toList();

        var json = new JSONObject();
        json.appendField( "result", attendances );

        return ResponseEntity.ok( json );
    }

    @PostMapping( path = "/manager/attendances/by_interval" )
    public ResponseEntity<JSONObject> getAttendanceListByInterval(
            @RequestBody AgentDTO agentDto, @RequestParam long start, @RequestParam long end ) {

        var json = new JSONObject();
        var intervalStart = new Timestamp( start );
        var intervalEnd = new Timestamp( end );

        if( intervalStart.after( intervalEnd ) ) {
            json.appendField( "result", Collections.EMPTY_LIST );
            return ResponseEntity.ok( json );
        }
        var mainDeptName = "Inova Macae";
        var deptName = agentDto.getDepartment().getName();

        if( deptName.equalsIgnoreCase( mainDeptName ) ) deptName = null;

        var attendances = module.getAttendanceService()
                                            .findAttendanceByInterval( intervalStart, intervalEnd, deptName )
                                            .parallelStream().map( AttendanceDTO::serialize ).toList();

        json.appendField( "result", attendances );
        return ResponseEntity.ok( json );
    }
}