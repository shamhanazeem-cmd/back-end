package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.GRNService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/grn")
public class GRNController {

    @Autowired
    private GRNService grnService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveGRN(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = grnService.saveGRN(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{grnId}")
    public ResponseEntity<StandardResponse> updateGRN(@RequestBody RequestRegistryDto data, @PathVariable String grnId){
        CommonResponseDto responseData = grnService.updateGRN(data, grnId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("{grnId}")
    public ResponseEntity<StandardResponse> deleteGRN(@PathVariable String grnId) throws SQLException {
        CommonResponseDto responseData = grnService.removeGRN(grnId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("{grnId}")
    public ResponseEntity<StandardResponse> getGRN(@PathVariable String grnId) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "GRN Data Found",
                        grnService.getGRNById(grnId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllGRNs() throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "GRN List",
                        grnService.allGRNs()),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedGRN(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Paged GRN List",
                        grnService.getAllPagedGRN(page, size)),
                HttpStatus.OK
        );
    }
}