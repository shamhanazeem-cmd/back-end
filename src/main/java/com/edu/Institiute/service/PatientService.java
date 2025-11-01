package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePatientDto;

import java.sql.SQLException;

public interface PatientService {
    CommonResponseDto savePatient(RequestRegistryDto dto);

    CommonResponseDto updatePatient(RequestRegistryDto data, String patientId);

    CommonResponseDto removePatient(String patientId);

    PaginatedResponsePatientDto patientById(String patientId) throws SQLException;

    PaginatedResponsePatientDto allPatient() throws SQLException;

    PaginatedResponsePatientDto getAllPagedPatient(int page, int size) throws SQLException;

}
