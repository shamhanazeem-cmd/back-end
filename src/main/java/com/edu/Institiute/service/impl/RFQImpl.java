package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.RFQDetailsDto;
import com.edu.Institiute.dto.RFQHeaderDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.RFQResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseRFQDto;
import com.edu.Institiute.entity.RFQDetails;
import com.edu.Institiute.entity.RFQHeader;
import com.edu.Institiute.entity.Status;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.RFQDetailsRepo;
import com.edu.Institiute.repo.RFQHeaderRepo;
import com.edu.Institiute.repo.StatusRepo;
import com.edu.Institiute.service.RFQService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.RFQMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CommonResponseDto updateRFQ(RequestRegistryDto dto, String rfqId) {
        try {
            Integer id = Integer.parseInt(rfqId);
            RFQHeader existingRfq = (RFQHeader) rfqHeaderRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("RFQ not found with ID: " + rfqId));

            existingRfq.setRfqNumber(dto.getRfqNumber());
            existingRfq.setRequestDate(dto.getRfqRequestDate());
            existingRfq.setRequiredDate(dto.getRfqRequiredDate());

            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());
            status.ifPresent(existingRfq::setStatus);

            List<RFQDetails> existingDetails = existingRfq.getDetails();

            if (existingDetails != null) {
                existingDetails.clear();
            } else {
                existingDetails = new ArrayList<>();
                existingRfq.setDetails(existingDetails);
            }

            if (dto.getRfqDetails() != null) {
                for (RFQDetailsDto itemDto : dto.getRfqDetails()) {
                    RFQDetails detail = new RFQDetails();
                    detail.setItem(itemDto.getItem());
                    detail.setQuantity(itemDto.getQuantity());
                    detail.setRemarks(itemDto.getRemarks());
                    detail.setRfqHeader(existingRfq);

                    existingDetails.add(detail);
                }
            }

            rfqHeaderRepo.save(existingRfq);

            return new CommonResponseDto(201, "RFQ Updated Successfully!", existingRfq.getRfqNumber(), new ArrayList<>());

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update RFQ because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removeRFQ(String rfqId) {
        try {
            Integer id = Integer.parseInt(rfqId);

            Optional<RFQHeader> rfq = rfqHeaderRepo.findRfqById(id);

            if (rfq.isPresent()) {
                rfqHeaderRepo.delete(rfq.get());

                return new CommonResponseDto(200, "RFQ deleted successfully!", true, new ArrayList<>());
            } else {
                throw new EntryNotFoundException("Can't find any RFQ with ID: " + rfqId);
            }
        } catch (NumberFormatException e) {
            throw new EntryNotFoundException("Invalid ID format. Please provide a numeric ID.");
        } catch (Exception e) {
            throw new EntryNotFoundException("Error occurred while deleting: " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponseRFQDto RFQById(String rfqId) {
        try {
            Integer id = Integer.parseInt(rfqId);
            Optional<RFQHeader> allRfqForProvidedId = rfqHeaderRepo.getAllRFQsForProvidedId(id);
            List<RFQResponseDto> rfqResponseDtos = new ArrayList<>();

            if (allRfqForProvidedId.isPresent()) {
                RFQHeader r = allRfqForProvidedId.get();

                List<RFQDetailsDto> detailDto = new ArrayList<>();
                if (r.getDetails() != null) {
                    for (RFQDetails d : r.getDetails()) {
                        detailDto.add(new RFQDetailsDto(0, d.getItem(), d.getQuantity(), d.getRemarks(), null));
                    }
                }


                rfqResponseDtos.add(
                        new RFQResponseDto(
                                r.getId(),
                                r.getRfqNumber(),
                                r.getRequestDate(),
                                r.getRequestedBy(),
                                r.getRequiredDate(),
                                detailDto,
                                statusMapper.toStatusDto(r.getStatus()),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        )
                );
            }

            return new PaginatedResponseRFQDto(
                    rfqHeaderRepo.count(),
                    rfqResponseDtos
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseRFQDto allRFQs() throws SQLException {
        try {
            List<RFQHeader> allRfqs = rfqHeaderRepo.findAll();
            List<RFQResponseDto> rfqResponseDtoList = new ArrayList<>();


            for (RFQHeader r : allRfqs) {
                List<RFQDetailsDto> detailDtos = new ArrayList<>();
                if (r.getDetails() != null) {
                    for (RFQDetails d : r.getDetails()) {
                        detailDtos.add(new RFQDetailsDto(
                                0,
                                d.getItem(),
                                d.getQuantity(),
                                d.getRemarks(),
                                null
                        ));
                    }
                }

                rfqResponseDtoList.add(
                        new RFQResponseDto(
                                r.getId(),
                                r.getRfqNumber(),
                                r.getRequestDate(),
                                r.getRequestedBy(),
                                r.getRequiredDate(),
                                detailDtos,
                                statusMapper.toStatusDto(r.getStatus()),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        )
                );

            }
            return new PaginatedResponseRFQDto(
                    rfqHeaderRepo.count(),
                    rfqResponseDtoList
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any RFQ data...!");

        }
    }

    @Override
    public PaginatedResponseRFQDto getAllPagedRFQ(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RFQHeader> RFQPage = rfqHeaderRepo.findAll(pageable);

            List<RFQResponseDto> rfqResponseDto = RFQPage.getContent()
                    .stream()
                    .map(r -> {
                        List<RFQDetailsDto> detailDtos = r.getDetails().stream()
                                .map(d -> new RFQDetailsDto(
                                        0,
                                        d.getItem(),
                                        d.getQuantity(),
                                        d.getRemarks(),
                                        null
                                ))
                                .collect(Collectors.toList());

                        return new RFQResponseDto(
                                r.getId(),
                                r.getRfqNumber(),
                                r.getRequestDate(),
                                r.getRequestedBy(),
                                r.getRequiredDate(),
                                detailDtos,
                                statusMapper.toStatusDto(r.getStatus()),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        );
                    })
                    .collect(Collectors.toList()); // Fixed this line

            return new PaginatedResponseRFQDto(
                    RFQPage.getNumberOfElements(),
                    rfqResponseDto,
                    RFQPage.getTotalPages(),
                    RFQPage.getTotalElements(),
                    RFQPage.getNumber(),
                    RFQPage.getSize(),
                    RFQPage.hasNext(),
                    RFQPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }

   }
