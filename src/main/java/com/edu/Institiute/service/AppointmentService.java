package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseAppointmentDto;

import java.sql.SQLException;

public interface AppointmentService {
    CommonResponseDto saveAppointment(RequestRegistryDto data);

    CommonResponseDto updateAppointment(RequestRegistryDto data, String appointmentId);

    CommonResponseDto removeAppointment(String appointmentId);

    PaginatedResponseAppointmentDto appointmentById(String appointmentId)  throws SQLException;

    PaginatedResponseAppointmentDto allAppointment() throws SQLException;

    PaginatedResponseAppointmentDto getAllPagedAppointment(int page, int size) throws SQLException;
}
