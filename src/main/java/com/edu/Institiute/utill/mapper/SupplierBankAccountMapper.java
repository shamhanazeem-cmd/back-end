package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SupplierBankAccountDTO;
import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;
import com.edu.Institiute.entity.SupplierBankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SupplierBankAccountMapper {

    SupplierBankAccountDTO toSupplierAccountDto(SupplierBankAccount bankAccount);

   // @Mapping(target = "supplier", ignore = true)
    SupplierBankAccount dtoToEntity(SupplierBankAccountDTO bankAccountDTO);
}
