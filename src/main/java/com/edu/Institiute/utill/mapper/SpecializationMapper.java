package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SpecializationDto;
import com.edu.Institiute.dto.StudentHasCourseDto;
import com.edu.Institiute.entity.Specialization;
import com.edu.Institiute.entity.StudentHasCourse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
@Mapper(componentModel = "spring")
public interface SpecializationMapper {
     Specialization dtoToSpecializationEntity(SpecializationDto specializationDto);

    //Set<SpecializationDto> toSpecializationDto(Specialization specialization);

    SpecializationDto toSpecializationDto(Specialization specialization);

}
