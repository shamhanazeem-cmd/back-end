package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseScheduleDto;

import java.sql.SQLException;

public interface ScheduleService {
    CommonResponseDto saveSchedule(RequestRegistryDto data);

    CommonResponseDto updateSchedule(RequestRegistryDto data, String scheduleId);

    CommonResponseDto removeSchedule(String scheduleId);

    PaginatedResponseScheduleDto scheduleById(String scheduleId)throws SQLException;

    PaginatedResponseScheduleDto allSchedule() throws SQLException;

    PaginatedResponseScheduleDto getAllPagedSchedule(int page, int size) throws SQLException ;


}
