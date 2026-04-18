package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.RFQService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/rfq")
public class RFQController {

    @Autowired
    private RFQService rfqService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> saveRFQ(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = rfqService.saveRFQ(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{rfqId}")
    public ResponseEntity<StandardResponse> updateRFQ(@RequestBody RequestRegistryDto data, @PathVariable String rfqId){
        CommonResponseDto responseData = rfqService.updateRFQ(data,rfqId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{rfqId}")
    public ResponseEntity<StandardResponse> deleteRFQ(@PathVariable String rfqId) throws SQLException {
        CommonResponseDto responseData = rfqService.removeRFQ(rfqId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{rfqId}")
    public ResponseEntity<StandardResponse> getRFQ(@PathVariable String rfqId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "RFQ List",
                        rfqService .RFQById(rfqId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllRFQs()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "RFQ  List",
                        rfqService .allRFQs()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedRFQ(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "RFQ List",
                        rfqService.getAllPagedRFQ(page, size)),
                HttpStatus.OK
        );
    }

}
