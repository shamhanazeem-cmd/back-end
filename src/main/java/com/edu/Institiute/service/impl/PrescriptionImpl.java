package com.edu.Institiute.service.impl;


import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.PrescriptionDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;


import com.edu.Institiute.dto.responseDto.PrescriptionResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePrescriptionDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.PrescriptionService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.stream.Collectors;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class PrescriptionImpl implements PrescriptionService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PrescriptionRepo prescriptionRepo;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;


    @Override
    public CommonResponseDto savePrescription(RequestRegistryDto dto) {
        try {
            int prescriptionId = generator.generateFourNumNumbers();
            Optional<Doctor> doctor = doctorRepo.findById(dto.getDoctor());
            Optional<Patient> patient = patientRepo.findById(dto.getPatient());
            Optional<Appointment> appointment = appointmentRepo.findById(dto.getAppointment());
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();

            PrescriptionDto prescriptionDto = new PrescriptionDto(
                    prescriptionId,
                    dto.getPrescriptionDate(),
                    dto.getNotes(),
                    createdBy,
                    new Date(),
                    "",
                    null,
                    doctorMapper.toDoctorDto(doctor.get()),
                    patientMapper.toPatientDto(patient.get()),
                    appointmentMapper.toAppointmentDto(appointment.get()),
                    statusMapper.toStatusDto(status.get())

            );
            prescriptionRepo.save(prescriptionMapper.dtoToPrescriptionEntity(prescriptionDto));

            return new CommonResponseDto(201, "Prescription saved!", prescriptionDto.getPrescriptionDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }


    @Override
    public CommonResponseDto updatePrescription(RequestRegistryDto dto, String prescriptionId) {
        try {

            Prescription allPrescriptionForProvidedId = prescriptionRepo.getAllPrescriptionForProvidedId(prescriptionId);
            allPrescriptionForProvidedId.setPrescriptionDate(dto.getPrescriptionDate());
            allPrescriptionForProvidedId.setNotes(dto.getNotes());

            return new CommonResponseDto(201, "Prescription  Updated!",  allPrescriptionForProvidedId.getPrescriptionDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removePrescription(String prescriptionId) {
        Optional<Prescription> prescription = prescriptionRepo.getPrescriptionById(prescriptionId);


        if (prescription.isPresent()) {
            prescriptionRepo.delete(prescription.get());
            return new CommonResponseDto(201, "prescription was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any prescription...!");
        }
    }

    @Override
    public PaginatedResponsePrescriptionDto PrescriptionById(String prescriptionId) throws SQLException {
        try {
            List<Prescription> allPrescriptionForProvidedId = prescriptionRepo.getAllPrescription(prescriptionId);
            List<PrescriptionResponseDto> prescriptionResponseDto = new ArrayList<>();


            for (Prescription r :allPrescriptionForProvidedId) {
                prescriptionResponseDto.add(
                        new PrescriptionResponseDto(
                                r.getId(),
                                r.getPrescriptionDate(),
                                r.getNotes(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctor()),
                                patientMapper.toPatientDto(r.getPatient()),
                                appointmentMapper.toAppointmentDto(r.getAppointment()),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            System.out.println(prescriptionResponseDto);
            return new PaginatedResponsePrescriptionDto(
                    prescriptionRepo.count(),
                    prescriptionResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponsePrescriptionDto allPrescription() throws SQLException {
        try {
            List<Prescription> allPrescriptionForProvidedId = prescriptionRepo.findAll();
            List<PrescriptionResponseDto> prescriptionResponseDto = new ArrayList<>();


            for (Prescription r :allPrescriptionForProvidedId) {
                prescriptionResponseDto.add(
                        new PrescriptionResponseDto(
                                r.getId(),
                                r.getPrescriptionDate(),
                                r.getNotes(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctor()),
                                patientMapper.toPatientDto(r.getPatient()),
                                appointmentMapper.toAppointmentDto(r.getAppointment()),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            System.out.println(prescriptionResponseDto);
            return new PaginatedResponsePrescriptionDto(
                    prescriptionRepo.count(),
                    prescriptionResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponsePrescriptionDto getAllPagedPrescription(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Prescription> prescriptionPage = prescriptionRepo.findAll(pageable);

            List<PrescriptionResponseDto> prescriptionResponseDto = prescriptionPage.getContent()
                    .stream()
                    .map(Prescription -> new PrescriptionResponseDto(
                            Prescription.getId(),
                            Prescription.getPrescriptionDate(),
                            Prescription.getNotes(),
                            Prescription.getCreatedBy(),
                            Prescription.getCreatedDate(),
                            Prescription.getModifyBy(),
                            Prescription.getModifyDate(),
                            doctorMapper.toDoctorDto(Prescription.getDoctor()),
                            patientMapper.toPatientDto(Prescription.getPatient()),
                            appointmentMapper.toAppointmentDto(Prescription.getAppointment()),
                            statusMapper.toStatusDto(Prescription.getStatus()
                            )
                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponsePrescriptionDto(
                    prescriptionPage.getNumberOfElements(),
                    prescriptionResponseDto,
                    prescriptionPage.getTotalPages(),
                    prescriptionPage.getTotalElements(),
                    prescriptionPage.getNumber(),
                    prescriptionPage.getSize(),
                    prescriptionPage.hasNext(),
                    prescriptionPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }




}
