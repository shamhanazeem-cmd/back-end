package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SupplierBankAccountDTO;
import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;
import com.edu.Institiute.entity.SupplierBankAccount;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SupplierBankAccountMapper {
    SupplierDTO toSupplierAccountDto(Supplier savedSupplier);

    SupplierBankAccount dtoToEntity(SupplierBankAccountDTO bankAccountDTO);
}
