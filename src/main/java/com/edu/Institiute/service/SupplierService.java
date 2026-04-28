package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierDTO;

import java.sql.SQLException;

public interface SupplierService {
    CommonResponseDto saveSupplier(RequestRegistryDto data);

    CommonResponseDto updateSupplier(RequestRegistryDto data, String supplierId);

    CommonResponseDto removeSupplier(String supplierId);

    PaginatedResponseSupplierDTO SupplierById(String supplierId) throws SQLException;

    PaginatedResponseSupplierDTO AllSuppliers() throws SQLException;

    PaginatedResponseSupplierDTO getAllPagedSuppliers(int page, int size) throws SQLException;
}
