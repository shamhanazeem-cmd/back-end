package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.RFQDetailsDto;
import com.edu.Institiute.dto.RFQHeaderDto;
import com.edu.Institiute.entity.RFQDetails;
import com.edu.Institiute.entity.RFQHeader;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
public interface RFQMapper {
    RFQHeader dtoToRFQHeaderEntity(RFQHeaderDto RFQHeaderDto);
    RFQHeaderDto toRFQHeaderDto(RFQHeader RFQHeader);

    RFQDetails dtoToRFQDetailsEntity(RFQDetailsDto RFQDetailsDto);
    RFQDetailsDto toRFQDetailsDto(RFQDetails RFQDetails);
}
