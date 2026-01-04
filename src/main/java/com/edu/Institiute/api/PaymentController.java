package com.edu.Institiute.api;



import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.MedicationService;
import com.edu.Institiute.service.PaymentService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/payment")


public class PaymentController {

    @Autowired
    private PaymentService PaymentService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping

    public ResponseEntity<StandardResponse> savePayment(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = PaymentService.savePayment(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{paymentId}")
    public ResponseEntity<StandardResponse> updatePayment(@RequestBody RequestRegistryDto data, @PathVariable String paymentId){
        CommonResponseDto responseData = PaymentService.updatePayment(data,paymentId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{paymentId}")
    public ResponseEntity<StandardResponse> deletePayment(@PathVariable String paymentId){
        CommonResponseDto responseData = PaymentService.removePayment(paymentId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{paymentId}")
    public ResponseEntity<StandardResponse> getPayment(@PathVariable String paymentId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Payment List",
                        PaymentService .paymentById(paymentId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllPayment()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Payment  List",
                        PaymentService .allPayment()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedPayment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Payment List",
                        PaymentService.getAllPagedPayment(page, size)),
                HttpStatus.OK
        );
    }


}
