package com.edu.Institiute.utill.mapper;


import com.edu.Institiute.dto.PatientDto;

import com.edu.Institiute.entity.Patient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")
public interface PatientMapper {
   Patient dtoToPatientEntity(PatientDto patientDto);

    PatientDto toPatientDto(Patient patient);

}
