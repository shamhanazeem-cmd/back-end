package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.MedicationDto;
import com.edu.Institiute.entity.Medication;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper(componentModel = "spring")

public interface MedicationMapper {
    Medication dtoToMedicationEntity(MedicationDto medicationDto);
}
