package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.RFQHeaderDto;
import com.edu.Institiute.dto.StatusDto;
import com.edu.Institiute.dto.SupplierQuotationDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierQuotationHeaderResponseDto {
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
