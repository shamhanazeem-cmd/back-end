package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.PrescriptionService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/prescription")


public class PrescriptionController {

    @Autowired
    private PrescriptionService PrescriptionService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> savePrescription(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = PrescriptionService.savePrescription(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{prescriptionId}")
    public ResponseEntity<StandardResponse> updatePrescription(@RequestBody RequestRegistryDto data, @PathVariable String prescriptionId){
        CommonResponseDto responseData = PrescriptionService.updatePrescription(data,prescriptionId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{prescriptionId}")
    public ResponseEntity<StandardResponse> deletePrescription(@PathVariable String prescriptionId){
        CommonResponseDto responseData = PrescriptionService.removePrescription(prescriptionId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{prescriptionId}")
    public ResponseEntity<StandardResponse> getPrescription(@PathVariable String prescriptionId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Prescription List",
                        PrescriptionService .PrescriptionById(prescriptionId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllPrescription()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Prescription  List",
                        PrescriptionService .allPrescription()),
                HttpStatus.OK
        );
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedPrescription(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Prescription List",
                        PrescriptionService.getAllPagedPrescription(page, size)),
                HttpStatus.OK
        );
    }

}


