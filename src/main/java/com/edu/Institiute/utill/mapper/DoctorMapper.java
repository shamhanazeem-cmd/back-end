package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.entity.Doctor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor dtoToDoctorEntity(DoctorDto doctorDto);

    DoctorDto toDoctorDto(Doctor doctor);

}
