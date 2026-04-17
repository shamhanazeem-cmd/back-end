package com.edu.Institiute.utill.mapper;



import com.edu.Institiute.dto.RFQDetailsDto;
import com.edu.Institiute.dto.RFQDto;
import com.edu.Institiute.entity.RFQDetails;
import com.edu.Institiute.entity.RFQHeader;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper(componentModel = "spring")
public interface RFQMapper {
  RFQHeader dtoToRFQEntity(RFQDto dto);

  RFQDto entityToRFQDto(RFQHeader entity);

  RFQDetails detailDtoToEntity(RFQDetailsDto detailDto);
  RFQDetailsDto detailEntityToDto(RFQDetails detailEntity);


}
