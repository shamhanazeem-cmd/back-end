package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePaymentDto;

import java.sql.SQLException;

public interface PaymentService {
    CommonResponseDto savePayment(RequestRegistryDto data);

    CommonResponseDto updatePayment(RequestRegistryDto data, String paymentId);

    CommonResponseDto removePayment(String paymentId);

    PaginatedResponsePaymentDto paymentById(String paymentId)throws SQLException;

    PaginatedResponsePaymentDto allPayment() throws SQLException;

    PaginatedResponsePaymentDto getAllPagedPayment(int page, int size) throws SQLException;
}
