package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierAddressDTO;

import java.sql.SQLException;

public interface SupplierAddressService {
    CommonResponseDto saveAddress(RequestRegistryDto data);

    CommonResponseDto updateAddress(RequestRegistryDto data, String addressId);

    CommonResponseDto removeAddress(String addressId);

    PaginatedResponseSupplierAddressDTO getAddressById(String addressId) throws SQLException;

    PaginatedResponseSupplierAddressDTO getAllAddresses() throws SQLException;

    PaginatedResponseSupplierAddressDTO getAllPagedAddresses(int page, int size) throws SQLException;
}
