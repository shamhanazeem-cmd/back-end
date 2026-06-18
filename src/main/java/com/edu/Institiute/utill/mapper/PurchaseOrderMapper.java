package com.edu.Institiute.utill.mapper;


import com.edu.Institiute.dto.PurchaseOrderDetailsDto;
import com.edu.Institiute.dto.PurchaseOrderHeaderDto;
import com.edu.Institiute.entity.PurchaseOrderDetails;
import com.edu.Institiute.entity.PurchaseOrderHeaader;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    // Header Mapping
    PurchaseOrderHeaader dtoToPOHeaderEntity(PurchaseOrderHeaderDto dto);
    PurchaseOrderHeaderDto poHeaderToDto(PurchaseOrderHeaader entity);

    // Detail Mapping
    PurchaseOrderDetails dtoToPODetailEntity(PurchaseOrderDetailsDto dto);
    PurchaseOrderDetailsDto poDetailToDto(PurchaseOrderDetails entity);

}

