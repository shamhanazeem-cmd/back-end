package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.service.MedicationService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/medication")


public class MedicationController {

    @Autowired
    private MedicationService MedicationService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping

    public ResponseEntity<StandardResponse> saveMedication(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = MedicationService.saveMedication(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{medicationId}")
    public ResponseEntity<StandardResponse> updateMedication(@RequestBody RequestRegistryDto data, @PathVariable String medicationId){
        CommonResponseDto responseData = MedicationService.updateMedication(data,medicationId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{medicationId}")
    public ResponseEntity<StandardResponse> deleteMedication(@PathVariable String medicationId){
        CommonResponseDto responseData = MedicationService.removeMedication(medicationId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{medicationId}")
    public ResponseEntity<StandardResponse> getMedication(@PathVariable String medicationId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medication List",
                        MedicationService .medicationById(medicationId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllMedication()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medication  List",
                        MedicationService .allMedication()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedMedication(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medication List",
                        MedicationService.getAllPagedMedication(page, size)),
                HttpStatus.OK
        );
    }


}
