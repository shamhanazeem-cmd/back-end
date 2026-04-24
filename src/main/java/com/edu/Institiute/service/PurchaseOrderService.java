package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePurchaseOrderHeaderDto;

import java.sql.SQLException;

public interface PurchaseOrderService {
    CommonResponseDto savePO(RequestRegistryDto data);

    CommonResponseDto updatePO(RequestRegistryDto data, String poId);

    CommonResponseDto removePO(String poId) throws SQLException;

    PaginatedResponsePurchaseOrderHeaderDto POById(String poId) throws SQLException;

    PaginatedResponsePurchaseOrderHeaderDto allPOs() throws SQLException;

    PaginatedResponsePurchaseOrderHeaderDto getAllPagedPO(int page, int size) throws SQLException;
}
