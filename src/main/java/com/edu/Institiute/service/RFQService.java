package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseRFQDto;

import java.sql.SQLException;

public interface RFQService {

    CommonResponseDto saveRFQ(RequestRegistryDto data);

    CommonResponseDto updateRFQ(RequestRegistryDto data, String rfqId);

    CommonResponseDto removeRFQ(String rfqId) throws SQLException;

    PaginatedResponseRFQDto RFQById(String rfqId) throws SQLException;

    PaginatedResponseRFQDto allRFQs() throws SQLException;

    PaginatedResponseRFQDto getAllPagedRFQ(int page, int size) throws SQLException;
}
