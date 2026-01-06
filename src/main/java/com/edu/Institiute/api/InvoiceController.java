package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.service.InvoiceService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService InvoiceService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping

    public ResponseEntity<StandardResponse> saveInvoice(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = InvoiceService.saveInvoice(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{invoiceId}")
    public ResponseEntity<StandardResponse> updateInvoice(@RequestBody RequestRegistryDto data, @PathVariable String invoiceId){
        CommonResponseDto responseData = InvoiceService.updateInvoice(data,invoiceId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }



    @DeleteMapping("{invoiceId}")
    public ResponseEntity<StandardResponse> deleteInvoice(@PathVariable String invoiceId){
        CommonResponseDto responseData = InvoiceService.removeInvoice(invoiceId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{invoiceId}")
    public ResponseEntity<StandardResponse> getInvoice(@PathVariable String invoiceId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Invoice List",
                        InvoiceService .invoiceById(invoiceId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllInvoice()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Invoice  List",
                        InvoiceService .allInvoices()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedInvoice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Invoice List",
                        InvoiceService.getAllPagedInvoice(page, size)),
                HttpStatus.OK
        );
    }





}
