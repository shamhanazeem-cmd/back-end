package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.MedicalHistoryService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/medicalhistory")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService MedicalHistoryService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> savedMedial(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = MedicalHistoryService.saveMedical(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }
    @PutMapping("{medicalHistoryId}")
    public ResponseEntity<StandardResponse> updateMedical(@RequestBody RequestRegistryDto data, @PathVariable String medicalHistoryId){
        CommonResponseDto responseData = MedicalHistoryService.updateMedical(data,medicalHistoryId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{medicalHistoryId}")
    public ResponseEntity<StandardResponse> deleteMedical(@PathVariable String medicalHistoryId){
        CommonResponseDto responseData = MedicalHistoryService.removeMedical(medicalHistoryId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{medicalHistoryId}")
    public ResponseEntity<StandardResponse> getMedical(@PathVariable String medicalHistoryId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Course List",
                        MedicalHistoryService .medicalHistoryById(medicalHistoryId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllMedical()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medical History  List",
                        MedicalHistoryService .allMedicalHistory()),
                HttpStatus.OK
        );
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedMedical(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Teacher List",
                        MedicalHistoryService.getAllPagedMedical(page, size)),
                HttpStatus.OK
        );
    }

}
