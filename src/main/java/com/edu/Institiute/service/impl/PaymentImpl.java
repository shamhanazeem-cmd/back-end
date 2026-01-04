package com.edu.Institiute.service.impl;



import com.edu.Institiute.dto.PaymentDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;


import com.edu.Institiute.dto.responseDto.PaymentResponseDto;
 import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePaymentDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.PaymentService;
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

public class PaymentImpl implements PaymentService {

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
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public CommonResponseDto savePayment(RequestRegistryDto dto) {
        try {
            int paymentId = generator.generateFourNumNumbers();
            Optional<Appointment> appointment = appointmentRepo.findById(dto.getAppointment());
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            PaymentDto paymentDto = new PaymentDto(
                    paymentId,
                    dto.getPaymentSerialID(),
                    dto.getHospitalCharge(),
                    dto.getDoctorCharge(),
                    dto.getTax(),
                    dto.getAmount(),
                    dto.getPaymentMethod(),
                    dto.getPaymentDate(),
                    dto.getCreatedBy(),
                    dto.getCreatedDate(),
                    dto.getModifyBy(),
                    dto.getModifyDate(),
                    statusMapper.toStatusDto(status.get()),
                    appointmentMapper.toAppointmentDto(appointment.get())

            );
            paymentRepo.save(paymentMapper.dtoToPaymentEntity(paymentDto));

            return new CommonResponseDto(201, "Payment saved!", paymentDto.getPaymentSerialID(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updatePayment(RequestRegistryDto dto, String paymentId) {
        try {

            Payment allPaymentForProvidedId = paymentRepo.getAllPaymentForProvidedId(paymentId);
            allPaymentForProvidedId.setPaymentSerialID(dto.getPaymentSerialID());
            allPaymentForProvidedId.setHospitalCharge(dto.getHospitalCharge());
            allPaymentForProvidedId.setDoctorCharge(dto.getDoctorCharge());
            allPaymentForProvidedId.setTax(dto.getTax());
            allPaymentForProvidedId.setAmount(dto.getAmount());
            allPaymentForProvidedId.setPaymentMethod(dto.getPaymentMethod());
            allPaymentForProvidedId.setPaymentDate(dto.getPaymentDate());

            return new CommonResponseDto(201, "Payment  Updated!",  allPaymentForProvidedId.getPaymentSerialID(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removePayment(String paymentId) {
        Optional<Payment> payment = paymentRepo.getPaymentById(paymentId);


        if (payment.isPresent()) {
            paymentRepo.delete(payment.get());
            return new CommonResponseDto(201, "payment was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any payment...!");
        }
    }

    @Override
    public PaginatedResponsePaymentDto paymentById(String paymentId) throws SQLException {
        try {
            List<Payment> allPaymentForProvidedId = paymentRepo.getAllpayment(paymentId);
            List<PaymentResponseDto> paymentResponseDto = new ArrayList<>();


            for (Payment r :allPaymentForProvidedId) {
                paymentResponseDto.add(
                        new PaymentResponseDto(
                                r.getId(),
                                r.getPaymentSerialID(),
                                r.getHospitalCharge(),
                                r.getDoctorCharge(),
                                r.getTax(),
                                r.getAmount(),
                                r.getPaymentMethod(),
                                r.getPaymentDate(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                appointmentMapper.toAppointmentDto(r.getAppointment())

                        )
                );
            }
            System.out.println(paymentResponseDto);
            return new PaginatedResponsePaymentDto(
                    paymentRepo.count(),
                    paymentResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponsePaymentDto allPayment() throws SQLException {
        try {
            List<Payment> allPaymentForProvidedId = paymentRepo.findAll();
            List<PaymentResponseDto> paymentResponseDto = new ArrayList<>();

            for (Payment r : allPaymentForProvidedId) {
                paymentResponseDto.add(
                        new PaymentResponseDto(
                                r.getId(),
                                r.getPaymentSerialID(),
                                r.getHospitalCharge(),
                                r.getDoctorCharge(),
                                r.getTax(),
                                r.getAmount(),
                                r.getPaymentMethod(),
                                r.getPaymentDate(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                appointmentMapper.toAppointmentDto(r.getAppointment())
                        )
                );
            }
            return new PaginatedResponsePaymentDto(
                    paymentRepo.count(),
                    paymentResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponsePaymentDto getAllPagedPayment(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Payment> paymentPage = paymentRepo.findAll(pageable);

            List<PaymentResponseDto> paymentResponseDto = paymentPage.getContent()
                    .stream()
                    .map(payment -> new PaymentResponseDto(
                            payment.getId(),
                            payment.getPaymentSerialID(),
                            payment.getHospitalCharge(),
                            payment.getDoctorCharge(),
                            payment.getTax(),
                            payment.getAmount(),
                            payment.getPaymentMethod(),
                            payment.getPaymentDate(),
                            payment.getCreatedBy(),
                            payment.getCreatedDate(),
                            payment.getModifyBy(),
                            payment.getModifyDate(),
                            statusMapper.toStatusDto(payment.getStatus()),
                            appointmentMapper.toAppointmentDto(payment.getAppointment())

                            )
                    )
                    .collect(Collectors.toList());

            return new PaginatedResponsePaymentDto(
                    paymentPage.getNumberOfElements(),
                    paymentResponseDto,
                    paymentPage.getTotalPages(),
                    paymentPage.getTotalElements(),
                    paymentPage.getNumber(),
                    paymentPage.getSize(),
                    paymentPage.hasNext(),
                    paymentPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }





}
