package com.edu.Institiute.api;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/doctor")

public class DoctorController {
    @Autowired
    private DoctorService DoctorService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping

    public ResponseEntity<StandardResponse> saveDoc(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = DoctorService.saveDoc(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{doctorId}")
    public ResponseEntity<StandardResponse> updateMedical(@RequestBody RequestRegistryDto data, @PathVariable String doctorId){
        CommonResponseDto responseData = DoctorService.updateDoc(data,doctorId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{doctorId}")
    public ResponseEntity<StandardResponse> deleteDoc(@PathVariable String doctorId){
        CommonResponseDto responseData = DoctorService.removeDoc(doctorId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{doctorId}")
    public ResponseEntity<StandardResponse> getDoc(@PathVariable String doctorId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Doctor List",
                        DoctorService .doctorById(doctorId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllDoc()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medical History  List",
                        DoctorService .allDoctors()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedDoc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Teacher List",
                        DoctorService.getAllPagedDoctor(page, size)),
                HttpStatus.OK
        );
    }


}
