package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.SpecializationDto;
import com.edu.Institiute.entity.Specialization;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper(componentModel = "spring")
public interface SpecializationMapper {
     Specialization dtoToSpecializationEntity(SpecializationDto specializationDto);
}
