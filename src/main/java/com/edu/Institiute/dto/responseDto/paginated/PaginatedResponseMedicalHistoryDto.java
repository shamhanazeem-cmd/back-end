package com.edu.Institiute.dto.responseDto.paginated;

import com.edu.Institiute.dto.responseDto.MedicalHistoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseMedicalHistoryDto {
    private Long count;
    private List<MedicalHistoryResponseDto> dataList;
}
