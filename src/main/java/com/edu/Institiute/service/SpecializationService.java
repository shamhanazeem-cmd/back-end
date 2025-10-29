package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicalHistoryDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSpecializationDto;

import java.sql.SQLException;


public interface SpecializationService {

    CommonResponseDto saveSpecialization(RequestRegistryDto dto);

    CommonResponseDto updateSpecialization(RequestRegistryDto dto, String specializationID);

    CommonResponseDto removeSpecialization(String specializationID);

    PaginatedResponseSpecializationDto specializationById(String specializationID) throws SQLException;

    PaginatedResponseSpecializationDto allSpecialization() throws SQLException;

    PaginatedResponseSpecializationDto getAllPagedSpecialization(int page, int size) throws SQLException;;


}
