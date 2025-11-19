package com.edu.Institiute.api;



import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.AppointmentService;
import com.edu.Institiute.service.ScheduleService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/appointment")

public class AppointmentController {

    @Autowired
    private AppointmentService AppointmentService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> saveAppointment(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = AppointmentService.saveAppointment(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{appointmentId}")
    public ResponseEntity<StandardResponse> updateAppointment(@RequestBody RequestRegistryDto data, @PathVariable String appointmentId){
        CommonResponseDto responseData = AppointmentService.updateAppointment(data,appointmentId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{appointmentId}")
    public ResponseEntity<StandardResponse> deleteAppointment(@PathVariable String appointmentId){
        CommonResponseDto responseData = AppointmentService.removeAppointment(appointmentId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{appointmentId}")
    public ResponseEntity<StandardResponse> getAppointment(@PathVariable String appointmentId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Appointment List",
                        AppointmentService .appointmentById(appointmentId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllAppointment()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Appointment List",
                        AppointmentService .allAppointment()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedAppointment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Appointment List",
                        AppointmentService.getAllPagedAppointment(page, size)),
                HttpStatus.OK
        );
    }

}
