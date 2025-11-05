package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.MedicalHistoryDto;
import com.edu.Institiute.entity.Doctor;
import com.edu.Institiute.entity.MedicalHistory;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper(componentModel = "spring")

public interface DoctorMapper {

    Doctor dtoToDoctorEntity(DoctorDto doctorDto);

}
