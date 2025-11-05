package com.edu.Institiute.service;


import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseDoctorDto;

import java.sql.SQLException;


public interface DoctorService {

    CommonResponseDto saveDoc(RequestRegistryDto data);

    CommonResponseDto updateDoc(RequestRegistryDto data, String doctorId);

    CommonResponseDto removeDoc(String doctorId);

    PaginatedResponseDoctorDto doctorById(String doctorId) throws SQLException;

    PaginatedResponseDoctorDto allDoctors() throws SQLException;

    PaginatedResponseDoctorDto  getAllPagedDoctor(int page, int size) throws SQLException;;
}
