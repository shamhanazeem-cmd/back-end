package com.edu.Institiute.dto.responseDto.paginated;


import com.edu.Institiute.dto.responseDto.PatientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponsePatientDto {
    private Long count;
    private List<PatientResponseDto> dataList;
}
