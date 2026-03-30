package com.edu.Institiute.service.impl;

import com.edu.Institiute.dto.responseDto.PatientMonthlyCountResponseDto;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.ReportPatientRepo;
import com.edu.Institiute.service.ReportPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportPatientRegistryImpl implements ReportPatientService {

    @Autowired
    private ReportPatientRepo reportPatientRepo;

    @Override
    public List<PatientMonthlyCountResponseDto> getPatientCountByMonth(String startDate, String endDate) {
        try {
            List<Object[]> results = reportPatientRepo.getPatientByMonth(startDate, endDate);
            List<PatientMonthlyCountResponseDto> response = new ArrayList<>();

            for (Object[] row : results) {
                Integer month = ((Number) row[0]).intValue();
                Long count = ((Number) row[1]).longValue();
                response.add(new PatientMonthlyCountResponseDto(month, count));
            }

            return response;
        } catch (Exception e) {
            throw new EntryNotFoundException(
                    "Unable to get patient by months → " + e.getMessage());
        }
    }
}
