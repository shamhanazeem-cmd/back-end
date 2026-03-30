package com.edu.Institiute.service;

import com.edu.Institiute.dto.responseDto.PatientMonthlyCountResponseDto;

import java.util.List;

public interface ReportPatientService {

    List<PatientMonthlyCountResponseDto> getPatientCountByMonth(String startDate, String endDate);
}
