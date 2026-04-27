package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;

import com.edu.Institiute.service.PurchaseOrderService;
import com.edu.Institiute.service.RFQService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/purchase_order")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> savePO(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = purchaseOrderService.savePO(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{poId}")
    public ResponseEntity<StandardResponse> updatePO(@RequestBody RequestRegistryDto data, @PathVariable String poId){
        CommonResponseDto responseData = purchaseOrderService.updatePO(data,poId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{poId}")
    public ResponseEntity<StandardResponse> deletePO(@PathVariable String poId) throws SQLException {
        CommonResponseDto responseData = purchaseOrderService.removePO(poId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{poId}")
    public ResponseEntity<StandardResponse> getPO(@PathVariable String poId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Purchase Order List",
                        purchaseOrderService .POById(poId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllPOs()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Purchase Order  List",
                        purchaseOrderService .allPOs()),
                HttpStatus.OK
        );
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedPO(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Purchase Order List",
                        purchaseOrderService.getAllPagedPO(page, size)),
                HttpStatus.OK
        );
    }
}
