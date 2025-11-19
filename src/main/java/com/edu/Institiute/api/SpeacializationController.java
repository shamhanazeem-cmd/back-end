package com.edu.Institiute.api;



import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.SpecializationService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/specialization")

public class SpeacializationController {

    @Autowired
    private SpecializationService SpecializationService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> saveSpecialization(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = SpecializationService.saveSpecialization(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{specializationID}")
    public ResponseEntity<StandardResponse> updateSpecialization(@RequestBody RequestRegistryDto data, @PathVariable String specializationID){
        CommonResponseDto responseData = SpecializationService.updateSpecialization(data,specializationID);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }


    @DeleteMapping("{specializationID}")
    public ResponseEntity<StandardResponse> deleteSpecialization(@PathVariable String specializationID){
        CommonResponseDto responseData = SpecializationService.removeSpecialization(specializationID);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{specializationID}")
    public ResponseEntity<StandardResponse> getSpecialization(@PathVariable String specializationID)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Course List",
                        SpecializationService .specializationById(specializationID)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllSpecialization()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Medical History  List",
                        SpecializationService .allSpecialization()),
                HttpStatus.OK
        );
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedSpecialization(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Specialization List",
                        SpecializationService.getAllPagedSpecialization(page, size)),
                HttpStatus.OK
        );
    }


}







