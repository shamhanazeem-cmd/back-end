package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "supplierId", ignore = true)
    Supplier dtoToEntity(SupplierDTO supplierDto);

    @Mapping(target = "supplierId", ignore = true)
    SupplierDTO toSupplierDto(Supplier supplier);
}
