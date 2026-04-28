package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderHeaderDto {
    private Long id;
    private String poNumber;
    private SupplierDTO supplier;
    private Date poDate;
    private Date expectedDate;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;
    private StatusDto status;
    private List<PurchaseOrderDetailsDto> PO_details;
}
