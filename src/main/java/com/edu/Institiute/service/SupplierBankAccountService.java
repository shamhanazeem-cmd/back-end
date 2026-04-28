package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierBankAccountDTO;

import java.sql.SQLException;

public interface SupplierBankAccountService {
    CommonResponseDto saveBankAccount(RequestRegistryDto data);

    CommonResponseDto updateBankAccount(RequestRegistryDto data, String bankAccountId);

    CommonResponseDto removeBankAccount(String bankAccountId);

    PaginatedResponseSupplierBankAccountDTO getBankAccountById(String bankAccountId) throws SQLException;

    PaginatedResponseSupplierBankAccountDTO allBankAccounts() throws SQLException;

    PaginatedResponseSupplierBankAccountDTO getAllPagedBankAccounts(int page, int size) throws SQLException;
}
