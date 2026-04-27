package com.edu.Institiute.dto.responseDto;

import com.edu.Institiute.dto.PurchaseOrderHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetailsResponseDto {
    private Integer id;
    private String poItem;
    private Integer orderedQuantity;
    private Double price;
    private Double total;
    private PurchaseOrderHeaderDto PO_Header;
}
