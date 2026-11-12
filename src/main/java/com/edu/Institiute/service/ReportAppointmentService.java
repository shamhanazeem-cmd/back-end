package com.edu.Institiute.service;

import com.edu.Institiute.dto.responseDto.DoctorAppointmentCountResponseDto;

import java.util.List;

public interface ReportAppointmentService {

    List<DoctorAppointmentCountResponseDto> getAppointmentCountByAllDoctors();

}
