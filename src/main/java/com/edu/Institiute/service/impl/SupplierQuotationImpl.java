package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.SupplierQuotationDetailDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.RFQResponseDto;
import com.edu.Institiute.dto.responseDto.SupplierQuotationHeaderResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierQuotationDetailDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierQuotationHeaderDto;
import com.edu.Institiute.entity.RFQHeader;
import com.edu.Institiute.entity.Status;
import com.edu.Institiute.entity.SupplierQuotationDetail;
import com.edu.Institiute.entity.SupplierQuotationHeader;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.SupplierQuotationService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.RFQMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import com.edu.Institiute.utill.mapper.SupplierQuotationMapper;
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
public class SupplierQuotationImpl implements SupplierQuotationService {
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

    @Autowired
    private SupplierQuotationMapper supplierQuotationMapper;

    @Autowired
    private SupplierQuotationDetailRepo supplierQuotationDetailRepo;

    @Autowired
    private SupplierQuotationHeaderRepo supplierQuotationHeaderRepo;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public CommonResponseDto saveQuotation(RequestRegistryDto data) {
        try {
            String quotationNumber = String.valueOf(generator.generateFourNumNumbers());
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : data.getCreatedBy();

            Optional<Status> status = statusRepo.findStatusById(data.getStatus());
            if (status.isEmpty()) {
                return new CommonResponseDto(400, "Invalid status ID", null, null);
            }

            Optional<RFQHeader> rfqHeader = rfqHeaderRepo.findRfqById(data.getRfq());
            if (rfqHeader.isEmpty()) {
                return new CommonResponseDto(400, "Invalid rfqHeader ID", null, null);
            }

            SupplierQuotationHeader header = new SupplierQuotationHeader();
            header.setQuotationNumber(data.getQuotationNumber());
            header.setSupplier(data.getSupplier());
            header.setDate(data.getDate());
            header.setCreatedBy(createdBy);
            header.setCreatedDate(new Date());
            header.setModifyBy("");
            header.setModifyDate(null);
            header.setStatus((status.get()));
            header.setRfq((rfqHeader.get()));

            SupplierQuotationHeader supplierQuotationHeader = supplierQuotationMapper.dtoToSQHeaderEntity(header);
            SupplierQuotationHeader savedHeader = supplierQuotationHeaderRepo.save(supplierQuotationHeader);

            //  Save Details
            if (data.getS_details() != null) {
                for (SupplierQuotationDetailDto detailDto : data.getS_details()) {
                    SupplierQuotationDetail detail = new SupplierQuotationDetail();
                    detail.setSQ_item(detailDto.getSQ_item());
                    detail.setQuotedPrice(detailDto.getQuotedPrice());
                    detail.setSQ_quantity(detailDto.getSQ_quantity());
                    detail.setDeliveryDays(detailDto.getDeliveryDays());
                    detail.setQuotationHeader(savedHeader);

                    supplierQuotationDetailRepo.save(detail);
                }
            }

            return new CommonResponseDto(201, "Quotation saved successfully", savedHeader.getQuotationNumber(), null);

        } catch (Exception e) {
            return new CommonResponseDto(500, "Failed to save: " + e.getMessage(), null, null);
        }
    }

    @Override
    public CommonResponseDto updateQuotation(RequestRegistryDto dto, String sqid) {
        try {
            Integer id = Integer.parseInt(sqid);

            SupplierQuotationHeader existingQuotation = supplierQuotationHeaderRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("Quotation not found with ID: " + sqid));


            existingQuotation.setQuotationNumber(dto.getQuotationNumber());
            existingQuotation.setSupplier(dto.getSupplier());
            existingQuotation.setDate(dto.getDate());

            RFQHeader linkedRfq = rfqHeaderRepo.findById(dto.getRfq())
                    .orElseThrow(() -> new EntryNotFoundException("RFQ not found with ID: " + dto.getRfq()));
            existingQuotation.setRfq(linkedRfq);

            List<SupplierQuotationDetail> existingDetails = existingQuotation.getS_details();

            if (existingDetails != null) {
                existingDetails.clear();
            } else {
                existingDetails = new ArrayList<>();
                existingQuotation.setS_details(existingDetails);
            }

            if (dto.getS_details() != null) {
                for (SupplierQuotationDetailDto itemDto : dto.getS_details()) {
                    SupplierQuotationDetail detail = new SupplierQuotationDetail();
                    detail.setSQ_item(itemDto.getSQ_item());
                    detail.setQuotedPrice(itemDto.getQuotedPrice());
                    detail.setSQ_quantity(itemDto.getSQ_quantity());
                    detail.setDeliveryDays(itemDto.getDeliveryDays());
                    detail.setQuotationHeader(existingQuotation); // Link to parent

                    existingDetails.add(detail);
                }
            }

            supplierQuotationHeaderRepo.save(existingQuotation);

            return new CommonResponseDto(201, "Quotation Updated Successfully!", existingQuotation.getQuotationNumber(), new ArrayList<>());

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update Quotation because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removeQuotation(String sqid) {
        try {
            Integer id = Integer.parseInt(sqid);

            Optional<SupplierQuotationHeader> quotation = supplierQuotationHeaderRepo.findSQById(id);

            if (quotation.isPresent()) {
                supplierQuotationHeaderRepo.delete(quotation.get());

                return new CommonResponseDto(200, "Quotation deleted successfully!", true, new ArrayList<>());
            } else {
                throw new EntryNotFoundException("Can't find any Quotation with ID: " + sqid);
            }
        } catch (NumberFormatException e) {
            throw new EntryNotFoundException("Invalid ID format. Please provide a numeric ID.");
        } catch (Exception e) {
            throw new EntryNotFoundException("Error occurred while deleting: " + e.getMessage());
        }
    }


    @Override
    public PaginatedResponseSupplierQuotationHeaderDto getQuotationById(String sqid) {
        try {
            Integer id = Integer.parseInt(sqid);
            Optional<SupplierQuotationHeader> quotation = supplierQuotationHeaderRepo.getAllSupplierQoutationForProvidedId(id);

            List<SupplierQuotationHeaderResponseDto> quotationResponseDtos = new ArrayList<>();

            if (quotation.isPresent()) {
                SupplierQuotationHeader q = quotation.get();

                // Map details
                List<SupplierQuotationDetailDto> detailDto = new ArrayList<>();
                if (q.getS_details() != null) {
                    for (SupplierQuotationDetail d : q.getS_details()) {
                        detailDto.add(new SupplierQuotationDetailDto(
                                d.getId(),
                                d.getSQ_item(),
                                d.getQuotedPrice(),
                                d.getSQ_quantity(),
                                d.getDeliveryDays(),
                                null
                        ));
                    }
                }

                quotationResponseDtos.add(
                        new SupplierQuotationHeaderResponseDto(
                                q.getId(),
                                q.getQuotationNumber(),
                                q.getSupplier(),
                                q.getDate(),
                                rfqMapper.toRFQHeaderDto(q.getRfq()),
                                statusMapper.toStatusDto(q.getStatus()),
                                detailDto,
                                q.getCreatedBy(),
                                q.getCreatedDate(),
                                q.getModifyBy(),
                                q.getModifyDate()
                        )
                );
            } else {
                throw new EntryNotFoundException("Quotation not found with ID: " + sqid);
            }

            return new PaginatedResponseSupplierQuotationHeaderDto(
                    supplierQuotationHeaderRepo.count(),
                    quotationResponseDtos
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseSupplierQuotationHeaderDto allQuotations() throws SQLException {
        try {
            List<SupplierQuotationHeader> allQuotations = supplierQuotationHeaderRepo.findAll();
            List<SupplierQuotationHeaderResponseDto> quotationResponseDtoList = new ArrayList<>();

            for (SupplierQuotationHeader q : allQuotations) {
                List<SupplierQuotationDetailDto> detailDtos = new ArrayList<>();

                // Map the details
                if (q.getS_details() != null) {
                    for (SupplierQuotationDetail d : q.getS_details()) {
                        detailDtos.add(new SupplierQuotationDetailDto(
                                d.getId(),
                                d.getSQ_item(),
                                d.getQuotedPrice(),
                                d.getSQ_quantity(),
                                d.getDeliveryDays(),
                                null
                        ));
                    }
                }

                // Map the Header
                quotationResponseDtoList.add(
                        new SupplierQuotationHeaderResponseDto(
                                q.getId(),
                                q.getQuotationNumber(),
                                q.getSupplier(),
                                q.getDate(),
                                rfqMapper.toRFQHeaderDto(q.getRfq()),
                                statusMapper.toStatusDto(q.getStatus()),
                                detailDtos,
                                q.getCreatedBy(),
                                q.getCreatedDate(),
                                q.getModifyBy(),
                                q.getModifyDate()
                        )
                );
            }

            return new PaginatedResponseSupplierQuotationHeaderDto(
                    supplierQuotationHeaderRepo.count(),
                    quotationResponseDtoList
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any Quotation data...!");
        }
    }

    @Override
    public PaginatedResponseSupplierQuotationHeaderDto getAllPagedQuotations(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SupplierQuotationHeader> quotationPage = supplierQuotationHeaderRepo.findAll(pageable);

            List<SupplierQuotationHeaderResponseDto> responseDtoList = quotationPage.getContent()
                    .stream()
                    .map(q -> {
                        // Map nested Details
                        List<SupplierQuotationDetailDto> detailDtos = q.getS_details().stream()
                                .map(d -> new SupplierQuotationDetailDto(
                                        d.getId(),
                                        d.getSQ_item(),
                                        d.getQuotedPrice(),
                                        d.getSQ_quantity(),
                                        d.getDeliveryDays(),
                                        null
                                ))
                                .collect(Collectors.toList());

                        // Map Header
                        return new SupplierQuotationHeaderResponseDto(
                                q.getId(),
                                q.getQuotationNumber(),
                                q.getSupplier(),
                                q.getDate(),
                                rfqMapper.toRFQHeaderDto(q.getRfq()),
                                statusMapper.toStatusDto(q.getStatus()),
                                detailDtos,
                                q.getCreatedBy(),
                                q.getCreatedDate(),
                                q.getModifyBy(),
                                q.getModifyDate()
                        );
                    })
                    .collect(Collectors.toList());

            return new PaginatedResponseSupplierQuotationHeaderDto(
                    quotationPage.getNumberOfElements(),
                    responseDtoList,
                    quotationPage.getTotalPages(),
                    quotationPage.getTotalElements(),
                    quotationPage.getNumber(),
                    quotationPage.getSize(),
                    quotationPage.hasNext(),
                    quotationPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any quotation data...!");
        }
    }


}
