package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.entity.Supplier;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier dtoToEntity(SupplierDTO supplierDto);

    SupplierDTO toSupplierDto(Supplier supplier);
}
