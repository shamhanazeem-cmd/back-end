package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.SupplierBankAccountService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/bank_accounts")
public class SupplierBankAccountController {

    @Autowired
    private SupplierBankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveBankAccount(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = bankAccountService.saveBankAccount(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{bankAccountId}")
    public ResponseEntity<StandardResponse> updateBankAccount(@RequestBody RequestRegistryDto data, @PathVariable String bankAccountId){
        CommonResponseDto responseData = bankAccountService.updateBankAccount(data, bankAccountId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{bankAccountId}")
    public ResponseEntity<StandardResponse> deleteBankAccount(@PathVariable String bankAccountId){
        CommonResponseDto responseData = bankAccountService.removeBankAccount(bankAccountId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("{bankAccountId}")
    public ResponseEntity<StandardResponse> getBankAccount(@PathVariable String bankAccountId) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Bank Account Found",
                        bankAccountService.getBankAccountById(bankAccountId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllBankAccounts() throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Bank Account List",
                        bankAccountService.allBankAccounts()),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedBankAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Bank Account List",
                        bankAccountService.getAllPagedBankAccounts(page, size)),
                HttpStatus.OK
        );
    }
}