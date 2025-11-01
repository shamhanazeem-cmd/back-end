package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.MedicalHistoryDto;
import com.edu.Institiute.entity.MedicalHistory;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper(componentModel = "spring")
public interface MedicalHistoryMapper {

    MedicalHistory dtoToMedicalHistoryEntity(MedicalHistoryDto medicalHistoryDto);

    MedicalHistoryDto entityToMedicalHistoryDTO(MedicalHistory medicalHistory);

    MedicalHistoryDto toMedicalHistoryDto(MedicalHistory medicalHistory);

}
