package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SupplierAddressDTO;
import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;
import com.edu.Institiute.entity.SupplierAddress;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


@Mapper(componentModel = "spring")
public interface SupplierAddressMapper {

    SupplierAddress dtoToEntity(SupplierAddressDTO supplierAddressDTO);

    SupplierDTO toSupplierAddressDto(Supplier savedSupplier);
}
