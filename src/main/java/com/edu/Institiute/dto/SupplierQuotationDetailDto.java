package com.edu.Institiute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierQuotationDetailDto {
    private Integer id;
    private String SQ_item;
    private Double quotedPrice;
    private Integer SQ_quantity;
    private Integer deliveryDays;
    private SupplierQuotationHeaderDto quotationHeader;
}
