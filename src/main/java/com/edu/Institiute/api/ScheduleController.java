package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.ScheduleService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService ScheduleService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> saveSchedule(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = ScheduleService.saveSchedule(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{scheduleId}")
    public ResponseEntity<StandardResponse> updateSchedule(@RequestBody RequestRegistryDto data, @PathVariable String scheduleId){
        CommonResponseDto responseData = ScheduleService.updateSchedule(data,scheduleId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<StandardResponse> deleteSchedule(@PathVariable String scheduleId){
        CommonResponseDto responseData = ScheduleService.removeSchedule(scheduleId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }


    @GetMapping("{scheduleId}")
    public ResponseEntity<StandardResponse> getSchedule(@PathVariable String scheduleId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Course List",
                        ScheduleService .scheduleById(scheduleId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllSchedule()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medical History  List",
                        ScheduleService .allSchedule()),
                HttpStatus.OK
        );
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedSchedule(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Schedule List",
                        ScheduleService.getAllPagedSchedule(page, size)),
                HttpStatus.OK
        );
    }
}

