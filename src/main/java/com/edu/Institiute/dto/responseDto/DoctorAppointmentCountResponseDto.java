package com.edu.Institiute.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAppointmentCountResponseDto {
    private String doctorName;
    private Long appointmentCount;
}
