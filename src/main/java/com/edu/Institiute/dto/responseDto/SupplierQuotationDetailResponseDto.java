package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.SupplierQuotationHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierQuotationDetailResponseDto {
    private Integer id;
    private String SQ_item;
    private Double quotedPrice;
    private Integer SQ_quantity;
    private Integer deliveryDays;
    private SupplierQuotationHeaderDto quotationHeader;
}
