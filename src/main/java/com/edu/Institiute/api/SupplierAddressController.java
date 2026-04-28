package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.SupplierAddressService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/address")
public class SupplierAddressController {

    @Autowired
    private SupplierAddressService addressService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveAddress(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = addressService.saveAddress(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{addressId}")
    public ResponseEntity<StandardResponse> updateAddress(@RequestBody RequestRegistryDto data, @PathVariable String addressId){
        CommonResponseDto responseData = addressService.updateAddress(data, addressId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{addressId}")
    public ResponseEntity<StandardResponse> deleteAddress(@PathVariable String addressId){
        CommonResponseDto responseData = addressService.removeAddress(addressId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("{addressId}")
    public ResponseEntity<StandardResponse> getAddress(@PathVariable String addressId) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Address Found",
                        addressService.getAddressById(addressId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllAddresses() throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Address List",
                        addressService.getAllAddresses()),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Address List",
                        addressService.getAllPagedAddresses(page, size)),
                HttpStatus.OK
        );
    }
}