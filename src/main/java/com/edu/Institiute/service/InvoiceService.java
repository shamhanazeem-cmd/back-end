package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseInvoiceDto;


import java.sql.SQLException;

public interface InvoiceService {
    CommonResponseDto saveInvoice(RequestRegistryDto data);

    CommonResponseDto updateInvoice(RequestRegistryDto data, String invoiceId);

    CommonResponseDto removeInvoice(String invoiceId);

    PaginatedResponseInvoiceDto invoiceById(String invoiceId) throws SQLException;

    PaginatedResponseInvoiceDto allInvoices() throws SQLException;

    PaginatedResponseInvoiceDto getAllPagedInvoice(int page, int size) throws SQLException;
}
