package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierQuotationDetailDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierQuotationHeaderDto;

import java.sql.SQLException;

public interface SupplierQuotationService {

    CommonResponseDto saveQuotation(RequestRegistryDto data);

    CommonResponseDto updateQuotation(RequestRegistryDto data, String sqid);

    CommonResponseDto removeQuotation(String sqid) throws SQLException;

    PaginatedResponseSupplierQuotationHeaderDto getQuotationById(String sqid) throws SQLException;

    PaginatedResponseSupplierQuotationHeaderDto allQuotations() throws SQLException;

    PaginatedResponseSupplierQuotationHeaderDto getAllPagedQuotations(int page, int size) throws SQLException;
}
