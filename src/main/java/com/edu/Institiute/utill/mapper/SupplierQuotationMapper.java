package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.entity.SupplierQuotationHeader;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierQuotationMapper {
    SupplierQuotationHeader dtoToSQHeaderEntity(SupplierQuotationHeader header);
}
