package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.GRNDetailsDto;
import com.edu.Institiute.dto.GRNDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.GRNResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseGRNDto;
import com.edu.Institiute.entity.*;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.GRNService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.GRNMapper;
import com.edu.Institiute.utill.mapper.PurchaseOrderMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import com.edu.Institiute.utill.mapper.SupplierMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GRNImpl implements GRNService {
    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private GRNRepo grnRepo;

    @Autowired
    private GRNDetailsRepo grnDetailsRepo;

    @Autowired
    private PurchaseOrderHeaderRepo poRepo;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private GRNMapper grnMapper;

    @Autowired
    private PurchaseOrderMapper poMapper;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public CommonResponseDto saveGRN(RequestRegistryDto data) {
        try {

            Optional<PurchaseOrderHeaader> poOpt = poRepo.findById(data.getPurchaseOrder());
            if (poOpt.isEmpty()) {
                return new CommonResponseDto(400, "Referenced Purchase Order not found", null, null);
            }
            PurchaseOrderHeaader po = poOpt.get();


            Optional<Supplier> supplierOpt = supplierRepo.findById(data.getGrn_Supplier());
            if (supplierOpt.isEmpty()) {
                return new CommonResponseDto(400, "Invalid Supplier ID: Supplier does not exist", null, null);
            }
            Supplier supplier = supplierOpt.get();


            if (!po.getSupplier().getSupplierId().equals(supplier.getSupplierId())) {
                return new CommonResponseDto(400, "Supplier mismatch! This PO belongs to " + po.getSupplier().getSupplierName(), null, null);
            }

            // 4. Generate Metadata
            String grnNumber = "GRN-" + generator.generateFourNumNumbers();
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : data.getCreatedBy();

            Optional<Status> status = statusRepo.findStatusById(data.getStatus());
            if (status.isEmpty()) {
                return new CommonResponseDto(400, "Invalid status ID", null, null);
            }


            GRNHeader grnHeaderEntity = new GRNHeader();
            grnHeaderEntity.setGrnNumber(grnNumber);
            grnHeaderEntity.setReceivedDate(new Date());
            grnHeaderEntity.setPurchaseOrder(po);
            grnHeaderEntity.setGrn_Supplier(supplier);
            grnHeaderEntity.setStatus(status.get());
            grnHeaderEntity.setCreatedBy(createdBy);
            grnHeaderEntity.setCreatedDate(new Date());
            grnHeaderEntity.setModifyBy("");

            GRNHeader savedHeader = grnRepo.save(grnHeaderEntity);

            //  Save Details with Business Logic (Accepted = Received - Damaged)
            if (data.getGrn_details() != null && !data.getGrn_details().isEmpty()) {
                for (GRNDetailsDto detailDto : data.getGrn_details()) {
                    GRNDetails detailEntity = new GRNDetails();
                    detailEntity.setGrnItem(detailDto.getGrn_Item());
                    detailEntity.setOrderedQty(detailDto.getOrderedQty());
                    detailEntity.setReceivedQty(detailDto.getReceivedQty());
                    detailEntity.setDamagedQty(detailDto.getDamagedQty());

                    int accepted = detailDto.getReceivedQty() - detailDto.getDamagedQty();
                    detailEntity.setAcceptedQty(accepted);

                    detailEntity.setGrnHeader(savedHeader);
                    grnDetailsRepo.save(detailEntity);

                }
            }

            return new CommonResponseDto(201, "GRN saved successfully", savedHeader.getGrnNumber(), null);

        } catch (Exception e) {
            return new CommonResponseDto(500, "Failed to save GRN: " + e.getMessage(), null, null);
        }

    }

    @Override
    public CommonResponseDto updateGRN(RequestRegistryDto data,String grnId){

        try {
            Integer id = Integer.parseInt(grnId);


            GRNHeader existingGrn = grnRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("GRN not found with ID: " + grnId));


            Optional<Status> status = statusRepo.findStatusById(data.getStatus());
            status.ifPresent(existingGrn::setStatus);


            String loggedUser = SecurityUtil.getLoggedUser();
            existingGrn.setModifyBy(loggedUser != null ? loggedUser : "System");
            existingGrn.setModifyDate(new Date());


            List<GRNDetails> existingDetails = existingGrn.getGrnDetails();
            if (existingDetails != null) {
                existingDetails.clear();
            } else {
                existingDetails = new ArrayList<>();
                existingGrn.setGrnDetails(existingDetails);
            }

            if (data.getGrn_details() != null) {
                for (GRNDetailsDto detailDto : data.getGrn_details()) {
                    GRNDetails detail = new GRNDetails();
                    detail.setGrnItem(detailDto.getGrn_Item());
                    detail.setOrderedQty(detailDto.getOrderedQty());
                    detail.setReceivedQty(detailDto.getReceivedQty());
                    detail.setDamagedQty(detailDto.getDamagedQty());


                    int accepted = detailDto.getReceivedQty() - detailDto.getDamagedQty();
                    detail.setAcceptedQty(accepted);


                    detail.setGrnHeader(existingGrn);
                    existingDetails.add(detail);


                }
            }


            grnRepo.save(existingGrn);

            return new CommonResponseDto(
                    200,
                    "GRN Updated Successfully!",
                    existingGrn.getGrnNumber(),
                    new ArrayList<>()
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update GRN because of this Error --> " + e.getMessage());
        }
    }


    @Override
    public CommonResponseDto removeGRN(String grnId){

        try {
            Integer id = Integer.parseInt(grnId);

            Optional<GRNHeader> grn = grnRepo.findById(id);

            if (grn.isPresent()) {
                grnRepo.delete(grn.get());

                return new CommonResponseDto(200, "GRN deleted successfully!", true, new ArrayList<>());
            } else {
                throw new EntryNotFoundException("Can't find any GRN with ID: " + grnId);
            }
        } catch (NumberFormatException e) {
            throw new EntryNotFoundException("Invalid ID format. Please provide a numeric ID.");
        } catch (Exception e) {
            throw new EntryNotFoundException("Error occurred while deleting: " + e.getMessage());
        }
    }


    @Override
    public PaginatedResponseGRNDto getGRNById(String grnId){
        try {
            Integer id = Integer.parseInt(grnId);

            Optional<GRNHeader> grnOpt = grnRepo.findById(id);
            List<GRNResponseDto> grnResponseDto = new ArrayList<>();

            if (grnOpt.isPresent()) {
                GRNHeader g = grnOpt.get();

                List<GRNDetailsDto> detailDto = new ArrayList<>();
                if (g.getGrnDetails() != null) {
                    for (GRNDetails d : g.getGrnDetails()) {
                        detailDto.add(new GRNDetailsDto(
                                d.getId(),
                                d.getGrnItem(),
                                d.getOrderedQty(),
                                d.getReceivedQty(),
                                d.getDamagedQty(),
                                d.getAcceptedQty(),
                               null
                        ));
                    }
                }

                grnResponseDto.add(
                        new GRNResponseDto(
                                g.getId(),
                                g.getGrnNumber(),
                                poMapper.poHeaderToDto(g.getPurchaseOrder()),
                                supplierMapper.toSupplierDto(g.getGrn_Supplier()),
                                g.getReceivedDate(),
                                detailDto,
                                statusMapper.toStatusDto(g.getStatus()),
                                g.getCreatedBy(),
                                g.getCreatedDate(),
                                g.getModifyBy(),
                                g.getModifyDate()
                        )
                );
            }

            // Return the count along with the list matching your DTO constructor signature
            return new PaginatedResponseGRNDto(
                    grnRepo.count(),
                    grnResponseDto
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any GRN data for provided ID...!");
        }

    }

    @Override
    public PaginatedResponseGRNDto allGRNs() throws SQLException {
        try {
            List<GRNHeader> allGrn = grnRepo.findAll();
            List<GRNResponseDto> grnResponseDtoList = new ArrayList<>();


            for (GRNHeader g : allGrn) {
                List<GRNDetailsDto> detailDto = new ArrayList<>();
                if (g.getGrnDetails() != null) {
                    for (GRNDetails d : g.getGrnDetails()) {

                        GRNDto parentDtoWrapper = new GRNDto();
                        parentDtoWrapper.setId(g.getId());

                        detailDto.add(new GRNDetailsDto(
                                d.getId(),
                                d.getGrnItem(),
                                d.getOrderedQty(),
                                d.getReceivedQty(),
                                d.getDamagedQty(),
                                d.getAcceptedQty(),
                                null
                        ));
                    }
                }

                grnResponseDtoList.add(
                        new GRNResponseDto(
                                g.getId(),
                                g.getGrnNumber(),
                                poMapper.poHeaderToDto(g.getPurchaseOrder()),
                                supplierMapper.toSupplierDto(g.getGrn_Supplier()),
                                g.getReceivedDate(),
                                detailDto,
                                statusMapper.toStatusDto(g.getStatus()),
                                g.getCreatedBy(),
                                g.getCreatedDate(),
                                g.getModifyBy(),
                                g.getModifyDate()
                        )
                );

            }
            return new PaginatedResponseGRNDto(
                    grnRepo.count(),
                    grnResponseDtoList
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any GRN data...!");

        }
    }

    @Override
    public PaginatedResponseGRNDto getAllPagedGRN(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<GRNHeader> GRNPage = grnRepo.findAll(pageable);

            List<GRNResponseDto> grnResponseDto = GRNPage.getContent()
                    .stream()
                    .map(g -> {
                        List<GRNDetailsDto> detailDto = g.getGrnDetails().stream()
                                .map(d -> new GRNDetailsDto(
                                        d.getId(),
                                        d.getGrnItem(),
                                        d.getOrderedQty(),
                                        d.getReceivedQty(),
                                        d.getDamagedQty(),
                                        d.getAcceptedQty(),
                                        null


                                ))
                                .collect(Collectors.toList());

                        return new GRNResponseDto(
                                g.getId(),
                                g.getGrnNumber(),
                                poMapper.poHeaderToDto(g.getPurchaseOrder()),
                                supplierMapper.toSupplierDto(g.getGrn_Supplier()),
                                g.getReceivedDate(),
                                detailDto,
                                statusMapper.toStatusDto(g.getStatus()),
                                g.getCreatedBy(),
                                g.getCreatedDate(),
                                g.getModifyBy(),
                                g.getModifyDate()
                        );
                    })
                    .collect(Collectors.toList());

            return new PaginatedResponseGRNDto(
                    GRNPage.getNumberOfElements(),
                    grnResponseDto,
                    GRNPage.getTotalPages(),
                    GRNPage.getTotalElements(),
                    GRNPage.getNumber(),
                    GRNPage.getSize(),
                    GRNPage.hasNext(),
                    GRNPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }
}
