package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.GRNDetailsDto;
import com.edu.Institiute.dto.PurchaseOrderHeaderDto;
import com.edu.Institiute.dto.StatusDto;
import com.edu.Institiute.dto.SupplierDTO;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class GRNResponseDto {
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
