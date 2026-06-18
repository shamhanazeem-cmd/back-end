package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseGRNDto;

import java.sql.SQLException;

public interface GRNService {
    CommonResponseDto saveGRN(RequestRegistryDto data);

    CommonResponseDto updateGRN(RequestRegistryDto data, String grnId);

    CommonResponseDto removeGRN(String grnId) throws SQLException;

    PaginatedResponseGRNDto getGRNById(String grnId) throws SQLException;

    PaginatedResponseGRNDto allGRNs() throws SQLException;

    PaginatedResponseGRNDto getAllPagedGRN(int page, int size) throws SQLException;
}
