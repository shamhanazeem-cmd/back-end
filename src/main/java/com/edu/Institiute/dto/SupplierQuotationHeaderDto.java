package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierQuotationHeaderDto {
    private Integer id;
    private String quotationNumber;
    private String supplier;
    private Date date;
    private RFQHeaderDto rfq;
    private StatusDto status;
    private List<SupplierQuotationDetailDto> S_details;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
}
