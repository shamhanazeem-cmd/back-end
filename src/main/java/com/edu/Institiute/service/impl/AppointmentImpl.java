package com.edu.Institiute.service.impl;


import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.AppointmentDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.AppointmentResponseDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;


import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseAppointmentDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.AppointmentService;
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

public class AppointmentImpl implements AppointmentService {

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
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;


    @Override
    public CommonResponseDto saveAppointment(RequestRegistryDto dto) {
        try {
            int appointmentId = generator.generateFourNumNumbers();
            Optional<Doctor> doctor = doctorRepo.findById(dto.getDoctorAppointment());
            Optional<Patient> patient = patientRepo.findById(dto.getPatient());
            Optional<Schedule> schedule = scheduleRepo.findById(dto.getSchedule());
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());


            if (doctor.isEmpty()) {
                throw new EntryNotFoundException("Doctor not found!");
            }
            if (patient.isEmpty()) {
                throw new EntryNotFoundException("Patient not found!");
            }
            if (schedule.isEmpty()) {
                throw new EntryNotFoundException("Schedule not found!");
            }

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();


            AppointmentDto appointmentDto = new AppointmentDto(
                    appointmentId,
                    dto.getAppointmentSerialID(),
                    dto.getAppointmentDate(),
                    dto.getAppointmentTime(),
                    createdBy,
                    new Date(),
                    "",
                    null,
                    doctorMapper.toDoctorDto(doctor.get()),
                    patientMapper.toPatientDto(patient.get()),
                    scheduleMapper.toScheduleDto(schedule.get()),
                    statusMapper.toStatusDto(status.get())

            );
            Appointment appointmentEntity = appointmentMapper.dtoToAppointmentEntity(appointmentDto);

            //  MANUALLY SET THE DOCTOR (This bypasses the broken Mapper)
            if (doctor.isPresent()) {
                appointmentEntity.setDoctorAppointment(doctor.get());
            }
            appointmentRepo.save(appointmentMapper.dtoToAppointmentEntity(appointmentDto));


            return new CommonResponseDto(201, "Appointment saved!", appointmentDto.getAppointmentDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updateAppointment(RequestRegistryDto dto, String appointmentId) {
        try {

            Appointment allAppointmentForProvidedId = appointmentRepo.getAllAppointmentForProvidedId(appointmentId);
            allAppointmentForProvidedId.setAppointmentSerialID(dto.getAppointmentSerialID());
            allAppointmentForProvidedId.setAppointmentDate(dto.getAppointmentDate());
            allAppointmentForProvidedId.setAppointmentTime(dto.getAppointmentTime());


            return new CommonResponseDto(201, "Appointment  Updated!",  allAppointmentForProvidedId.getAppointmentDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeAppointment(String appointmentId) {
        Optional<Appointment> appointment = appointmentRepo.getAppointmentById(appointmentId);


        if (appointment.isPresent()) {
            appointmentRepo.delete(appointment.get());
            return new CommonResponseDto(201, "appointment was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any appointment...!");
        }
    }

    @Override
    public PaginatedResponseAppointmentDto appointmentById(String appointmentId) throws SQLException {
        try {
            List<Appointment> allAppointmentForProvidedId = appointmentRepo.getAllAppointment(appointmentId);
            List<AppointmentResponseDto> appointmentResponseDto = new ArrayList<>();


            for (Appointment r :allAppointmentForProvidedId) {
                appointmentResponseDto.add(
                        new AppointmentResponseDto(
                                r.getId(),
                                r.getAppointmentSerialID(),
                                r.getAppointmentDate(),
                                r.getAppointmentTime(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctorAppointment()),
                                patientMapper.toPatientDto(r.getPatient()),
                                scheduleMapper.toScheduleDto(r.getSchedule()),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            System.out.println(appointmentResponseDto);
            return new PaginatedResponseAppointmentDto(
                    appointmentRepo.count(),
                    appointmentResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseAppointmentDto allAppointment() throws SQLException {
        try {
            List<Appointment> allAppointmentForProvidedId = appointmentRepo.findAll();
            List<AppointmentResponseDto> appointmentResponseDto = new ArrayList<>();

            for (Appointment r : allAppointmentForProvidedId) {
                appointmentResponseDto.add(
                        new AppointmentResponseDto(
                                r.getId(),
                                r.getAppointmentSerialID(),
                                r.getAppointmentDate(),
                                r.getAppointmentTime(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctorAppointment()),
                                patientMapper.toPatientDto(r.getPatient()),
                                scheduleMapper.toScheduleDto(r.getSchedule()),
                                statusMapper.toStatusDto(r.getStatus())
                        )
                );
            }
            return new PaginatedResponseAppointmentDto(
                    appointmentRepo.count(),
                    appointmentResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseAppointmentDto getAllPagedAppointment(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Appointment> appointmentPage = appointmentRepo.findAll(pageable);

            List<AppointmentResponseDto> appointmentResponseDto = appointmentPage.getContent()
                    .stream()
                    .map(appointment -> new AppointmentResponseDto(
                            appointment.getId(),
                            appointment.getAppointmentSerialID(),
                            appointment.getAppointmentDate(),
                            appointment.getAppointmentTime(),
                            appointment.getCreatedBy(),
                            appointment.getCreatedDate(),
                            appointment.getModifyBy(),
                            appointment.getModifyDate(),
                            doctorMapper.toDoctorDto(appointment.getDoctorAppointment()),
                            patientMapper.toPatientDto(appointment.getPatient()),
                            scheduleMapper.toScheduleDto(appointment.getSchedule()),
                            statusMapper.toStatusDto(appointment.getStatus()
                            )
                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponseAppointmentDto(
                    appointmentPage.getNumberOfElements(),
                    appointmentResponseDto,
                    appointmentPage.getTotalPages(),
                    appointmentPage.getTotalElements(),
                    appointmentPage.getNumber(),
                    appointmentPage.getSize(),
                    appointmentPage.hasNext(),
                    appointmentPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }



}
