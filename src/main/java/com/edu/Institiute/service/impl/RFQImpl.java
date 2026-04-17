package com.edu.Institiute.service.impl;


import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.RFQDetailsDto;
import com.edu.Institiute.dto.RFQDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.entity.RFQDetails;
import com.edu.Institiute.entity.Specialization;
import com.edu.Institiute.entity.Status;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.RFQHeaderRepo;
import com.edu.Institiute.repo.StatusRepo;
import com.edu.Institiute.service.RFQService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.RFQMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RFQImpl implements RFQService {
    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RFQHeaderRepo rfqHeaderRepo;

    @Autowired
    private RFQMapper rfqMapper;

    @Override
    public CommonResponseDto saveRFQ(RequestRegistryDto dto) {
        try {
            String rfqId = generator.generateFourNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            List<RFQDetailsDto> detailDtos = new ArrayList<>();
            if (dto.getRfqDetails() != null) {
                for (RFQDetailsDto item : dto.getRfqDetails()) {
                    RFQDetailsDto dDto = new RFQDetailsDto();
                    dDto.setItem(item.getItem());
                    dDto.setQuantity(item.getQuantity());
                    dDto.setRemarks(item.getRemarks());
                    detailDtos.add(dDto);
                }
            }

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();

            RFQDto rfqDto = new RFQDto(
                    rfqId,
                    dto.getRfqNumber(),
                    dto.getRfqRequestDate(),
                    dto.getRfqRequiredDate(),
                    detailDtos,
                    statusMapper.toStatusDto(status.get())

            );

            rfqHeaderRepo.save(rfqMapper.dtoToRFQEntity(rfqDto));

            return new CommonResponseDto(201, "RFQ  saved!", rfqDto.getRfqNumber(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }








}
