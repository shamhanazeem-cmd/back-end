package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.PurchaseOrderDetailsDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.PurchaseOrderHeaderResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePurchaseOrderHeaderDto;
import com.edu.Institiute.entity.*;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.PurchaseOrderService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.PurchaseOrderMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
import com.edu.Institiute.utill.mapper.SupplierMapper;
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
public class PurchaseOrderImpl implements PurchaseOrderService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private PurchaseOrderHeaderRepo purchaseOrderHeaderRepo;

    @Autowired
    private PurchaseOrderDetailsRepo purchaseOrderDetailsRepo;

    @Autowired
    private StatusMapper statusMapper;

   // @Autowired
   // private PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public CommonResponseDto savePO(RequestRegistryDto data) {
        try {
            String poNumber = "PO-" + generator.generateFourNumNumbers();
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : "Admin";

            Optional<Status> status = statusRepo.findStatusById(data.getStatus());
            if (status.isEmpty()) return new CommonResponseDto(400, "Invalid status ID", null, null);

            Optional<Supplier> supplier = supplierRepo.findById(data.getSupplier());
            if (supplier.isEmpty()) {
                return new CommonResponseDto(400, "Invalid Supplier ID", null, null);
            }

            // Mapping Header
            PurchaseOrderHeaader poHeader = new PurchaseOrderHeaader();
            poHeader.setPoNumber(poNumber);
            poHeader.setSupplier(supplier.get());
            poHeader.setPoDate(data.getPoDate());
            poHeader.setExpectedDate(data.getExpectedDate());
            poHeader.setCreatedBy(createdBy);
            poHeader.setCreatedDate(new Date());
            poHeader.setStatus(status.get());

            PurchaseOrderHeaader savedHeader = purchaseOrderHeaderRepo.save(poHeader);

            // Mapping Details with Calculation
            if (data.getPO_details() != null) {
                for (PurchaseOrderDetailsDto d : data.getPO_details()) {
                    PurchaseOrderDetails detail = new PurchaseOrderDetails();
                    detail.setPoItem(d.getPoItem());
                    detail.setOrderedQuantity(d.getOrderedQuantity());
                    detail.setPrice(d.getPrice());
                    // Business Logic: Calculation
                    detail.setTotal(d.getOrderedQuantity() * d.getPrice());
                    detail.setPO_Header(savedHeader);
                    purchaseOrderDetailsRepo.save(detail);
                }
            }
            return new CommonResponseDto(201, "PO saved successfully", poNumber, null);
        } catch (Exception e) {
            return new CommonResponseDto(500, "Failed to save PO: " + e.getMessage(), null, null);
        }
    }

    @Override
    public CommonResponseDto updatePO(RequestRegistryDto dto, String poId) {
        try {
            Integer id = Integer.parseInt(poId);
            PurchaseOrderHeaader existingPo = purchaseOrderHeaderRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("PO not found"));

            Supplier linkedSupplier = supplierRepo.findById(dto.getSupplier())
                    .orElseThrow(() -> new EntryNotFoundException("Supplier not found with ID: " + dto.getSupplier()));
            existingPo.setSupplier(linkedSupplier);

            existingPo.setExpectedDate(dto.getExpectedDate());

            // Handle Details update
            if (existingPo.getPO_details() != null) existingPo.getPO_details().clear();

            if (dto.getPO_details() != null) {
                for (PurchaseOrderDetailsDto d : dto.getPO_details()) {
                    PurchaseOrderDetails detail = new PurchaseOrderDetails();
                    detail.setPoItem(d.getPoItem());
                    detail.setOrderedQuantity(d.getOrderedQuantity());
                    detail.setPrice(d.getPrice());
                    detail.setTotal(d.getOrderedQuantity() * d.getPrice()); // Recalculate
                    detail.setPO_Header(existingPo);
                    existingPo.getPO_details().add(detail);
                }
            }
            purchaseOrderHeaderRepo.save(existingPo);
            return new CommonResponseDto(201, "PO Updated!", existingPo.getPoNumber(), null);
        } catch (Exception e) {
            throw new EntryNotFoundException("Update failed: " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removePO(String poId) {
        try {
            Integer id = Integer.parseInt(poId);

            Optional<PurchaseOrderHeaader> po = purchaseOrderHeaderRepo.findPoById(id);

            if (po.isPresent()) {
                purchaseOrderHeaderRepo.delete(po.get());

                return new CommonResponseDto(200, "Purchase Order deleted successfully!", true, new ArrayList<>());
            } else {
                throw new EntryNotFoundException("Can't find any Purchase Order with ID: " + poId);
            }
        } catch (NumberFormatException e) {
            throw new EntryNotFoundException("Invalid ID format. Please provide a numeric ID.");
        } catch (Exception e) {
            throw new EntryNotFoundException("Error occurred while deleting: " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponsePurchaseOrderHeaderDto POById(String poId) {
        try {
            Integer id = Integer.parseInt(poId);
            Optional<PurchaseOrderHeaader> poHeaderOpt = purchaseOrderHeaderRepo.getAllRFQsForProvidedId(id);
            List<PurchaseOrderHeaderResponseDto> poResponseDtos = new ArrayList<>();

            if (poHeaderOpt.isPresent()) {
                PurchaseOrderHeaader po = poHeaderOpt.get();

                List<PurchaseOrderDetailsDto> detailDtoList = new ArrayList<>();
                if (po.getPO_details() != null) {
                    for (PurchaseOrderDetails d : po.getPO_details()) {
                        detailDtoList.add(new PurchaseOrderDetailsDto(
                                0,
                                d.getPoItem(),
                                d.getOrderedQuantity(),
                                d.getPrice(),
                                d.getTotal(),
                                null

                        ));
                    }
                }

                poResponseDtos.add(
                        new PurchaseOrderHeaderResponseDto(
                                po.getId(),
                                po.getPoNumber(),
                                supplierMapper.toSupplierDto(po.getSupplier()),
                                po.getPoDate(),
                                po.getExpectedDate(),
                                po.getCreatedBy(),
                                po.getCreatedDate(),
                                po.getModifyBy(),
                                po.getModifyDate(),
                                statusMapper.toStatusDto(po.getStatus()),
                                detailDtoList
                        )
                );
            }

            return new PaginatedResponsePurchaseOrderHeaderDto(
                    purchaseOrderHeaderRepo.count(),
                    poResponseDtos
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any Purchase Order data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponsePurchaseOrderHeaderDto allPOs() throws SQLException {
        try {
            // 1. Fetch all PO headers
            List<PurchaseOrderHeaader> allPOs = purchaseOrderHeaderRepo.findAll();
            List<PurchaseOrderHeaderResponseDto> poResponseDtoList = new ArrayList<>();

            // 2. Iterate and map to DTOs
            for (PurchaseOrderHeaader p : allPOs) {
                List<PurchaseOrderDetailsDto> detailDtos = new ArrayList<>();

                // Map details
                if (p.getPO_details() != null) {
                    for (PurchaseOrderDetails d : p.getPO_details()) {
                        detailDtos.add(new PurchaseOrderDetailsDto(
                                0,
                                d.getPoItem(),
                                d.getOrderedQuantity(),
                                d.getPrice(),
                                d.getTotal() ,
                                null
                        ));
                    }
                }

                // Map Header
                poResponseDtoList.add(
                        new PurchaseOrderHeaderResponseDto(
                                p.getId(),
                                p.getPoNumber(),
                                supplierMapper.toSupplierDto(p.getSupplier()),
                                p.getPoDate(),
                                p.getExpectedDate(),
                                p.getCreatedBy(),
                                p.getCreatedDate(),
                                p.getModifyBy(),
                                p.getModifyDate(),
                                statusMapper.toStatusDto(p.getStatus()),
                                detailDtos
                        )
                );
            }

            return new PaginatedResponsePurchaseOrderHeaderDto(
                    purchaseOrderHeaderRepo.count(),
                    poResponseDtoList
            );

        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any Purchase Order data...!");
        }
    }

    @Override
    public PaginatedResponsePurchaseOrderHeaderDto getAllPagedPO(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PurchaseOrderHeaader> poPage = purchaseOrderHeaderRepo.findAll(pageable);
            List<PurchaseOrderHeaderResponseDto> poResponseDto = poPage.getContent()
                    .stream()
                    .map(p -> {
                        // Map details using your specific PO fields
                        List<PurchaseOrderDetailsDto> detailDtos = p.getPO_details().stream()
                                .map(d -> new PurchaseOrderDetailsDto(
                                        0,
                                        d.getPoItem(),
                                        d.getOrderedQuantity(),
                                        d.getPrice(),
                                        d.getTotal(),
                                        null
                                ))
                                .collect(Collectors.toList());

                        // Map Header to DTO
                        return new PurchaseOrderHeaderResponseDto(
                                p.getId(),
                                p.getPoNumber(),
                                supplierMapper.toSupplierDto(p.getSupplier()),
                                p.getPoDate(),
                                p.getExpectedDate(),
                                p.getCreatedBy(),
                                p.getCreatedDate(),
                                p.getModifyBy(),
                                p.getModifyDate(),
                                statusMapper.toStatusDto(p.getStatus()),
                                detailDtos
                        );
                    })
                    .collect(Collectors.toList());

            return new PaginatedResponsePurchaseOrderHeaderDto(
                    poPage.getNumberOfElements(),
                    poResponseDto,
                    poPage.getTotalPages(),
                    poPage.getTotalElements(),
                    poPage.getNumber(),
                    poPage.getSize(),
                    poPage.hasNext(),
                    poPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any Purchase Order data...!");
        }
    }


}
