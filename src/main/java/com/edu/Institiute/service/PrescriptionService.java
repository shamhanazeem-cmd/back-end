package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePrescriptionDto;

import java.sql.SQLException;
public interface PrescriptionService {
     CommonResponseDto savePrescription(RequestRegistryDto data) ;

     CommonResponseDto updatePrescription(RequestRegistryDto data, String prescriptionId);

     CommonResponseDto removePrescription(String prescriptionId);

     PaginatedResponsePrescriptionDto PrescriptionById(String prescriptionId) throws SQLException;

     PaginatedResponsePrescriptionDto allPrescription() throws SQLException;

     PaginatedResponsePrescriptionDto getAllPagedPrescription(int page, int size) throws SQLException;
}
