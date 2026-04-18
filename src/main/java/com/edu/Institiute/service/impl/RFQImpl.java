package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.RFQDetailsDto;
import com.edu.Institiute.dto.RFQHeaderDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseRFQDto;
import com.edu.Institiute.entity.RFQDetails;
import com.edu.Institiute.entity.RFQHeader;
import com.edu.Institiute.entity.Status;
import com.edu.Institiute.repo.RFQDetailsRepo;
import com.edu.Institiute.repo.RFQHeaderRepo;
import com.edu.Institiute.repo.StatusRepo;
import com.edu.Institiute.service.RFQService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.RFQMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class RFQImpl implements RFQService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private RFQHeaderRepo rfqHeaderRepo;

    @Autowired
    private RFQDetailsRepo rfqDetailsRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private RFQMapper rfqMapper;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public CommonResponseDto saveRFQ(RequestRegistryDto data) {
        try {
            String rfqNumber = String.valueOf(generator.generateFourNumNumbers());
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : data.getCreatedBy();

            Optional<Status> status = statusRepo.findStatusById(data.getStatus());
            if (status.isEmpty()) {
                return new CommonResponseDto(400, "Invalid status ID", null, null);
            }

            RFQHeaderDto rfqHeaderDto = new RFQHeaderDto();
            rfqHeaderDto.setRfqNumber(rfqNumber);
            rfqHeaderDto.setRequestDate(data.getRfqRequestDate());
            rfqHeaderDto.setRequestedBy(data.getRfqRequestedBy());
            rfqHeaderDto.setRequiredDate(data.getRfqRequiredDate());
            rfqHeaderDto.setCreatedBy(createdBy);
            rfqHeaderDto.setCreatedDate(new Date());
            rfqHeaderDto.setModifyBy("");
            rfqHeaderDto.setModifyDate(null);
            rfqHeaderDto.setStatus(statusMapper.toStatusDto(status.get()));

            RFQHeader rfqHeaderEntity = rfqMapper.dtoToRFQHeaderEntity(rfqHeaderDto);
            RFQHeader savedHeader = rfqHeaderRepo.save(rfqHeaderEntity);

            if (data.getRfqDetails() != null && !data.getRfqDetails().isEmpty()) {
                for (RFQDetailsDto detailDto : data.getRfqDetails()) {
                    detailDto.setRfqHeader(rfqMapper.toRFQHeaderDto(savedHeader));
                    RFQDetails detailEntity = rfqMapper.dtoToRFQDetailsEntity(detailDto);
                    rfqDetailsRepo.save(detailEntity);
                }
            }

            return new CommonResponseDto(201, "RFQ saved successfully", savedHeader.getRfqNumber(), null);

        } catch (Exception e) {
            return new CommonResponseDto(500, "Failed to save RFQ: " + e.getMessage(), null, null);
        }
    }

    @Override
    public CommonResponseDto updateRFQ(RequestRegistryDto data, String rfqId) {
        return null;
    }

    @Override
    public CommonResponseDto removeRFQ(String rfqId) throws SQLException {
        return null;
    }

    @Override
    public PaginatedResponseRFQDto RFQById(String rfqId) throws SQLException {
        return null;
    }

    @Override
    public PaginatedResponseRFQDto allRFQs() throws SQLException {
        return null;
    }

    @Override
    public PaginatedResponseRFQDto getAllPagedRFQ(int page, int size) throws SQLException {
        return null;
    }

}
