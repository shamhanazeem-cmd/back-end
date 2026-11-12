package com.edu.Institiute.api;

import com.edu.Institiute.repo.ReportAppointmentRepo;
import com.edu.Institiute.service.ReportAppointmentService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/report/appointment")
public class ReportAppointmentController {
    @Autowired
    private ReportAppointmentRepo reportAppointmentRepo;

    @Autowired
    private ReportAppointmentService reportAppointmentService;


    @GetMapping("/appointmentcountbyalldoctors")
    public ResponseEntity<StandardResponse> getAppointmentCountByAllDoctors() {

        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Appointment Counts By All Doctors",
                        reportAppointmentService.getAppointmentCountByAllDoctors()
                ),
                HttpStatus.OK
        );
    }

}

