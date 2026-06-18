package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GRNDto {
    private Integer id;
    private String grnNumber;
    private PurchaseOrderHeaderDto purchaseOrder;
    private SupplierDTO grn_Supplier;
    private Date receivedDate;
    private List<GRNDetailsDto> details;
    private StatusDto status;
    private String createdBy;
    private Date createdDate;
    private String modifyBy;
    private Date modifyDate;

}
