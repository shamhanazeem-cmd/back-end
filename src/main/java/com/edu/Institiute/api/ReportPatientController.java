package com.edu.Institiute.api;

import com.edu.Institiute.repo.ReportPatientRepo;
import com.edu.Institiute.service.ReportPatientService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/report/patient")
public class ReportPatientController {


    @Autowired
    private ReportPatientRepo reportCustomerRepo;

    @Autowired
    private ReportPatientService reportCustomerService;

    @GetMapping("/count")
    public ResponseEntity<StandardResponse> getAllPatientCount() {

        Long totalPatient = reportCustomerRepo.countAllPatient();

        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Total Patient Count",
                        totalPatient
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/customerbymonth")
    public ResponseEntity<StandardResponse> getPatientByMonth(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Patient Report List By Month",
                        reportCustomerService.getPatientCountByMonth(startDate, endDate)),
                HttpStatus.OK);
    }
}
