package com.edu.Institiute.service.impl;


import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.DoctorResponseDto;

import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseDoctorDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.DoctorRepo;
import com.edu.Institiute.repo.SpecializationRepo;
import com.edu.Institiute.repo.StatusRepo;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.DoctorMapper;
import com.edu.Institiute.utill.mapper.SpecializationMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
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
public class DoctorImpl implements DoctorService {

    @Autowired
    private Generator generator;


    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private SpecializationRepo specializationRepo;

    @Autowired
    private SpecializationMapper specializationMapper;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private DoctorMapper doctorMapper;



    @Override
    public CommonResponseDto saveDoc(RequestRegistryDto dto) {
        try {
            int doctorId = generator.generateFourNumNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());
            Optional<Specialization> specialization = specializationRepo.findSpecializationById(dto.getSpecializations());

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();

                DoctorDto doctorDto = new DoctorDto(
                        doctorId,
                        dto.getDoctorSerialID(),
                        dto.getDoctorName(),
                        dto.getContactDetails(),
                        dto.getMail(),
                        dto.getRoomNo(),
                        createdBy,
                        new Date(),
                        "",
                        null,
                        statusMapper.toStatusDto(status.get()),
                        specializationMapper.toSpecializationDto(specialization.get())
                );

            doctorRepo.save(doctorMapper.dtoToDoctorEntity(doctorDto));

            return new CommonResponseDto(201, "Doctor  saved!", doctorDto.getDoctorName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }


    @Override
    public CommonResponseDto updateDoc(RequestRegistryDto dto, String doctorId) {
        try {

            Doctor allDoctorsForProvidedId = doctorRepo.getAllDoctorsForProvidedId(doctorId);
            allDoctorsForProvidedId.setDoctorSerialID(dto.getDoctorSerialID());
            allDoctorsForProvidedId.setDoctorName(dto.getDoctorName());
            allDoctorsForProvidedId.setContactDetails(dto.getContactDetails());
            allDoctorsForProvidedId.setMail(dto.getMail());
            allDoctorsForProvidedId.setRoomNo(dto.getRoomNo());


            doctorRepo.save( allDoctorsForProvidedId);
            return new CommonResponseDto(201, "Doctor  Updated!",  allDoctorsForProvidedId.getDoctorName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeDoc(String doctorId) {
        Optional<Doctor> doctor = doctorRepo.getDoctorById(doctorId);

        if (doctor.isPresent()) {
            doctorRepo.delete(doctor.get());
            return new CommonResponseDto(201, "doctor was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any doctor...!");
        }
    }

    @Override
    public PaginatedResponseDoctorDto doctorById(String doctorId) throws SQLException {
        try {
            List<Doctor> allDoctorsForProvidedId = doctorRepo.getDoctorDetailById(doctorId);
            List<DoctorResponseDto> doctorResponseDto = new ArrayList<>();


            for (Doctor r :allDoctorsForProvidedId) {
                doctorResponseDto.add(
                        new DoctorResponseDto(
                                r.getId(),
                                r.getDoctorSerialID(),
                                r.getDoctorName(),
                                r.getContactDetails(),
                                r.getMail(),
                                r.getRoomNo(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                specializationMapper.toSpecializationDto(r.getSpecializations())

                        )
                );
            }
            System.out.println(doctorResponseDto);
            return new PaginatedResponseDoctorDto(
                    doctorRepo.count(),
                    doctorResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseDoctorDto allDoctors() throws SQLException {
        try {
            List<Doctor> allDoctorsForProvidedId = doctorRepo.findAll();
            List<DoctorResponseDto> doctorResponseDto = new ArrayList<>();

            for (Doctor r : allDoctorsForProvidedId) {
                doctorResponseDto.add(
                        new DoctorResponseDto(
                                r.getId(),
                                r.getDoctorSerialID(),
                                r.getDoctorName(),
                                r.getContactDetails(),
                                r.getMail(),
                                r.getRoomNo(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                specializationMapper.toSpecializationDto(r.getSpecializations())


                        )
                );
            }
            return new PaginatedResponseDoctorDto(
                    doctorRepo.count(),
                    doctorResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseDoctorDto getAllPagedDoctor(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Doctor> doctorPage = doctorRepo.findAll(pageable);

            List<DoctorResponseDto> doctorResponseDto = doctorPage.getContent()
                    .stream()
                    .map(doctor -> new DoctorResponseDto(
                                    doctor.getId(),
                                    doctor.getDoctorSerialID(),
                                    doctor.getDoctorName(),
                                    doctor.getContactDetails(),
                                    doctor.getMail(),
                                    doctor.getRoomNo(),
                                    doctor.getCreatedBy(),
                                    doctor.getCreatedDate(),
                                    doctor.getModifyBy(),
                                    doctor.getModifyDate(),
                                    statusMapper.toStatusDto(doctor.getStatus()),
                                    specializationMapper.toSpecializationDto(doctor.getSpecializations())
                            )
                    )
                    .collect(Collectors.toList()); // Fixed this line

            return new PaginatedResponseDoctorDto(
                    doctorPage.getNumberOfElements(),
                    doctorResponseDto,
                    doctorPage.getTotalPages(),
                    doctorPage.getTotalElements(),
                    doctorPage.getNumber(),
                    doctorPage.getSize(),
                    doctorPage.hasNext(),
                    doctorPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }


}
