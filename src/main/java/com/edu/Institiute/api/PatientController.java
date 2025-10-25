package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.entity.Patient;
import com.edu.Institiute.service.MedicalHistoryService;
import com.edu.Institiute.service.PatientService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    @Autowired
    private PatientService PatientService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> savedPatient(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = PatientService.savePatient(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{patientId}")
    public ResponseEntity<StandardResponse> updatePatient(@RequestBody RequestRegistryDto data, @PathVariable String patientId){
        CommonResponseDto responseData = PatientService.updatePatient(data,patientId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{patientId}")
    public ResponseEntity<StandardResponse> deletePatient(@PathVariable String patientId){
        CommonResponseDto responseData = PatientService.removePatient(patientId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{patientId}")
    public ResponseEntity<StandardResponse> getPatient(@PathVariable String patientId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Patient List",
                        PatientService.patientById(patientId)),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllPatient()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        " Patient  List",
                        PatientService.allPatient()),
                HttpStatus.OK
        );
    }

}

