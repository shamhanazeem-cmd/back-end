package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.SupplierService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveSupplier(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = supplierService.saveSupplier(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{supplierId}")
    public ResponseEntity<StandardResponse> updateSupplier(@RequestBody RequestRegistryDto data, @PathVariable String supplierId){
        CommonResponseDto responseData = supplierService.updateSupplier(data, supplierId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{supplierId}")
    public ResponseEntity<StandardResponse> deleteSupplier(@PathVariable String supplierId){
        CommonResponseDto responseData = supplierService.removeSupplier(supplierId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("{supplierId}")
    public ResponseEntity<StandardResponse> getSupplier(@PathVariable String supplierId) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Supplier Found",
                        supplierService.SupplierById(supplierId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllSuppliers() throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Supplier List",
                        supplierService.AllSuppliers()),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Supplier List",
                        supplierService.getAllPagedSuppliers(page, size)),
                HttpStatus.OK
        );
    }
}