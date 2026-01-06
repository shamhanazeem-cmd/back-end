package com.edu.Institiute.service.impl;



import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.InvoiceDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.AppointmentResponseDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.DoctorResponseDto;

import com.edu.Institiute.dto.responseDto.InvoiceResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseAppointmentDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseDoctorDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseInvoiceDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.service.InvoiceService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class InvoiceImpl implements InvoiceService {

    @Autowired
    private Generator generator;


    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceMapper invoiceMapper;


    @Override
    public CommonResponseDto saveInvoice(RequestRegistryDto dto) {
        try {
            int invoiceId = generator.generateFourNumNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            Optional<Payment> payment = paymentRepo.findById(dto.getPayment());

            InvoiceDto invoiceDto = new InvoiceDto(
                    invoiceId,
                    dto.getInvoiceNumber(),
                    dto.getIssuedDate(),
                    dto.getTotalAmount(),
                    dto.getCreatedBy(),
                    dto.getCreatedDate(),
                    dto.getModifyBy(),
                    dto.getModifyDate(),
                    paymentMapper.toPaymentDto(payment.get()),
                    statusMapper.toStatusDto(status.get())

            );

            invoiceRepo.save(invoiceMapper.dtoToInvoiceEntity(invoiceDto));

            return new CommonResponseDto(201, "Invoice  saved!", invoiceDto.getInvoiceNumber(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updateInvoice(RequestRegistryDto dto, String invoiceId) {
        try {

            Invoice allInvoiceForProvidedId = invoiceRepo.getAllInvoiceForProvidedId(invoiceId);
            allInvoiceForProvidedId.setInvoiceNumber(dto.getInvoiceNumber());
            allInvoiceForProvidedId.setIssuedDate(dto.getIssuedDate());
            allInvoiceForProvidedId.setTotalAmount(dto.getTotalAmount());

            return new CommonResponseDto(201, "Invoice  Updated!",  allInvoiceForProvidedId.getInvoiceNumber(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeInvoice(String invoiceId) {
        Optional<Invoice> invoice = invoiceRepo.getInvoiceById(invoiceId);


        if (invoice.isPresent()) {
            invoiceRepo.delete(invoice.get());
            return new CommonResponseDto(201, "Invoice was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any Invoice...!");
        }
    }

    @Override
    public PaginatedResponseInvoiceDto invoiceById(String invoiceId) throws SQLException {
        try {
            List<Invoice> allInvoiceForProvidedId = invoiceRepo.getAllInvoice(invoiceId);
            List<InvoiceResponseDto> invoiceResponseDto = new ArrayList<>();


            for (Invoice r :allInvoiceForProvidedId) {
                invoiceResponseDto.add(
                        new InvoiceResponseDto(
                                r.getId(),
                                r.getInvoiceNumber(),
                                r.getIssuedDate(),
                                r.getTotalAmount(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                paymentMapper.toPaymentDto(r.getPayment()),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            System.out.println(invoiceResponseDto);
            return new PaginatedResponseInvoiceDto(
                    invoiceRepo.count(),
                    invoiceResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseInvoiceDto allInvoices() throws SQLException {
        try {
            List<Invoice> allInvoiceForProvidedId = invoiceRepo.findAll();
            List<InvoiceResponseDto> invoiceResponseDto = new ArrayList<>();

            for (Invoice r :allInvoiceForProvidedId) {
                invoiceResponseDto.add(
                        new InvoiceResponseDto(
                                r.getId(),
                                r.getInvoiceNumber(),
                                r.getIssuedDate(),
                                r.getTotalAmount(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                paymentMapper.toPaymentDto(r.getPayment()),
                                statusMapper.toStatusDto(r.getStatus())
                        )
                );
            }
            return new PaginatedResponseInvoiceDto(
                    invoiceRepo.count(),
                    invoiceResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseInvoiceDto getAllPagedInvoice(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Invoice> invoicePage = invoiceRepo.findAll(pageable);

            List<InvoiceResponseDto> invoiceResponseDto = invoicePage.getContent()
                    .stream()
                    .map(invoice -> new InvoiceResponseDto(
                            invoice.getId(),
                            invoice.getInvoiceNumber(),
                            invoice.getIssuedDate(),
                            invoice.getTotalAmount(),
                            invoice.getCreatedBy(),
                            invoice.getCreatedDate(),
                            invoice.getModifyBy(),
                            invoice.getModifyDate(),
                            paymentMapper.toPaymentDto(invoice.getPayment()),
                            statusMapper.toStatusDto(invoice.getStatus()
                            )
                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponseInvoiceDto(
                    invoicePage.getNumberOfElements(),
                    invoiceResponseDto,
                    invoicePage.getTotalPages(),
                    invoicePage.getTotalElements(),
                    invoicePage.getNumber(),
                    invoicePage.getSize(),
                    invoicePage.hasNext(),
                    invoicePage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }



}
