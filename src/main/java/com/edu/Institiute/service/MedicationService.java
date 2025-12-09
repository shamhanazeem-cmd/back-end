package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicationDto;

import java.sql.SQLException;

public interface MedicationService {

    CommonResponseDto saveMedication(RequestRegistryDto data);

    CommonResponseDto updateMedication(RequestRegistryDto data, String medicationId);

    CommonResponseDto removeMedication(String medicationId);

    PaginatedResponseMedicationDto medicationById(String medicationId) throws SQLException;

    PaginatedResponseMedicationDto allMedication() throws SQLException;

    PaginatedResponseMedicationDto getAllPagedMedication(int page, int size) throws SQLException;


}
