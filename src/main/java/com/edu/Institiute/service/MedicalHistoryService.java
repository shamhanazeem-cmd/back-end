package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicalHistoryDto;

import java.sql.SQLException;

public interface MedicalHistoryService {




    CommonResponseDto saveMedical(RequestRegistryDto dto);

    CommonResponseDto updateMedical(RequestRegistryDto dto, String medicalHistoryId);

    CommonResponseDto removeMedical(String medicalHistoryId);

    PaginatedResponseMedicalHistoryDto medicalHistoryById(String medicalHistoryId) throws SQLException;

    PaginatedResponseMedicalHistoryDto allMedicalHistory() throws SQLException;
}
