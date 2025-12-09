package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.PrescriptionDto;
import com.edu.Institiute.entity.Prescription;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    Prescription dtoToPrescriptionEntity(PrescriptionDto prescriptionDto);

    PrescriptionDto toPrescriptionDto(Prescription prescription);
}
