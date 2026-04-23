package com.edu.Institiute.api;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.SupplierQuotationService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/supplierquotation")
public class SupplierQuotationController {
    @Autowired
    private SupplierQuotationService quotationService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveQuotation(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = quotationService.saveQuotation(data);
        return new ResponseEntity<>(
                new StandardResponse(responseData.getCode(), responseData.getMessage(), responseData.getData()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{sqid}")
    public ResponseEntity<StandardResponse> updateQuotation(@RequestBody RequestRegistryDto data, @PathVariable String sqid){
        CommonResponseDto responseData = quotationService.updateQuotation(data, sqid);
        return new ResponseEntity<>(
                new StandardResponse(responseData.getCode(), responseData.getMessage(), responseData.getData()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{sqid}")
    public ResponseEntity<StandardResponse> deleteQuotation(@PathVariable String sqid) throws SQLException {
        CommonResponseDto responseData = quotationService.removeQuotation(sqid);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{sqid}")
    public ResponseEntity<StandardResponse> getQuotation(@PathVariable String sqid) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(200, "Quotation Details", quotationService.getQuotationById(sqid)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllQuotations() throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(200, "All Quotations", quotationService.allQuotations()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedQuotations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Quotations List",
                        quotationService.getAllPagedQuotations(page, size)),
                HttpStatus.OK
        );
    }
}
