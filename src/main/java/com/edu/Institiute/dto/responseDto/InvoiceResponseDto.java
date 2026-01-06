package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.PaymentDto;
import com.edu.Institiute.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceResponseDto {
    private Integer id;
    private String invoiceNumber;
    private Date issuedDate;
    private Integer totalAmount;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private PaymentDto payment;
    private StatusDto status;
}
