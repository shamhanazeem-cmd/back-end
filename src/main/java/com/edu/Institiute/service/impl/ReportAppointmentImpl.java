package com.edu.Institiute.service.impl;

import com.edu.Institiute.dto.responseDto.DoctorAppointmentCountResponseDto;
import com.edu.Institiute.repo.ReportAppointmentRepo;
import com.edu.Institiute.service.ReportAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportAppointmentImpl implements ReportAppointmentService {
    @Autowired
    private ReportAppointmentRepo reportAppointmentRepo;

    @Override
    public List<DoctorAppointmentCountResponseDto> getAppointmentCountByAllDoctors() {

        List<Object[]> results = reportAppointmentRepo.getAppointmentCountByAllDoctors();

        List<DoctorAppointmentCountResponseDto> response = new ArrayList<>();

        for (Object[] row : results) {

            String doctorName = (String) row[0];

            Long count = ((Number) row[1]).longValue();

            response.add(
                    new DoctorAppointmentCountResponseDto(
                            doctorName,
                            count
                    )
            );
        }

        return response;
    }

}
