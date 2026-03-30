package com.edu.Institiute.service.impl;



import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.PatientDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;

import com.edu.Institiute.dto.responseDto.MedicalHistoryResponseDto;
import com.edu.Institiute.dto.responseDto.PatientResponseDto;

import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicalHistoryDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePatientDto;

import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Patient;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.MedicalHistoryRepo;
import com.edu.Institiute.repo.PatientRepo;

import com.edu.Institiute.service.PatientService;
import com.edu.Institiute.utill.Generator;

import com.edu.Institiute.utill.mapper.MedicalHistoryMapper;
import com.edu.Institiute.utill.mapper.PatientMapper;



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
public class PatientImpl implements PatientService {

    @Autowired
    private Generator generator;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private MedicalHistoryRepo medicalRepo;

    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public CommonResponseDto savePatient(RequestRegistryDto dto) {

        Optional<MedicalHistory> medicalHistory = medicalRepo.findById(dto.getPatientMedicalHistory());

        String loggedUser = SecurityUtil.getLoggedUser();
        String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();


        try {
            int patientId =  generator.generateFourNumNumbers();
            String newPatientSerialID =  "Patient"+"-" + generator.generateFourNumbers();
            PatientDto patientDto = new PatientDto(
                    patientId,
                    newPatientSerialID,
                    dto.getFullName(),
                    dto.getNic(),
                    dto.getDob(),
                    dto.getGender(),
                    dto.getAddress(),
                    dto.getContactNo(),
                    dto.getEmail(),
                    medicalHistoryMapper.entityToMedicalHistoryDTO(medicalHistory.get()),
                    createdBy,
                    new Date(),
                    "",
                    null
            );

        patientRepo.save(patientMapper.dtoToPatientEntity(patientDto));

        return new CommonResponseDto(201, "Patient  saved!", patientDto.getFullName(), new ArrayList<>());
    }catch (Exception e){
        throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
    }

    }

    @Override
    public CommonResponseDto updatePatient(RequestRegistryDto dto, String patientId) {
        try {


            Patient allPatientForProvidedId = patientRepo.getAllPatientForProvidedId(patientId);
            allPatientForProvidedId.setPatientSerialID(dto.getPatientSerialID());
            allPatientForProvidedId.setFullName(dto.getFullName());
            allPatientForProvidedId.setNic(dto.getNic());
            allPatientForProvidedId.setDob(dto.getDob());
            allPatientForProvidedId.setContactNo(dto.getContactNo());
            allPatientForProvidedId.setAddress(dto.getAddress());
            allPatientForProvidedId.setEmail(dto.getEmail());

            patientRepo.save( allPatientForProvidedId );
            return new CommonResponseDto(201, "Patient  Updated!",  allPatientForProvidedId.getFullName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removePatient(String patientId) {
        Optional<Patient> patient = patientRepo.getPatientById(patientId);



        if (patient.isPresent()) {
            patientRepo.delete(patient.get());
            return new CommonResponseDto(201, "Patient was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any Student...!");
        }
    }

    @Override
    public PaginatedResponsePatientDto patientById(String patientId) throws SQLException {
        try {
            List<Patient> allPatientForProvidedId = patientRepo.getPatientDetailById(patientId);
            List<PatientResponseDto> patientResponseDto = new ArrayList<>();


            for (Patient r :allPatientForProvidedId) {
                patientResponseDto.add(
                        new PatientResponseDto(
                                r.getId(),
                                r.getPatientSerialID(),
                                r.getFullName(),
                                r.getNic(),
                                r.getDob(),
                                r.getGender(),
                                r.getAddress(),
                                r.getContactNo(),
                                r.getEmail(),
                                medicalHistoryMapper.toMedicalHistoryDto(r.getMedicalHistory()),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        )
                );
            }
            System.out.println(patientResponseDto);
            return new PaginatedResponsePatientDto(
                    patientRepo.count(),
                    patientResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }


    @Override
    public PaginatedResponsePatientDto allPatient() throws SQLException {
        try {
            List<Patient> allPatientForProvidedId = patientRepo.findAll();
            List<PatientResponseDto> patientResponseDto = new ArrayList<>();

            for (Patient r : allPatientForProvidedId) {
                patientResponseDto.add(
                        new PatientResponseDto(
                                r.getId(),
                                r.getPatientSerialID(),
                                r.getFullName(),
                                r.getNic(),
                                r.getDob(),
                                r.getGender(),
                                r.getAddress(),
                                r.getContactNo(),
                                r.getEmail(),
                                medicalHistoryMapper.toMedicalHistoryDto(r.getMedicalHistory()),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        )
                );
            }
            return new PaginatedResponsePatientDto(
                    patientRepo.count(),
                    patientResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponsePatientDto getAllPagedPatient(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Patient> patientPage = patientRepo.findAll(pageable);

            List<PatientResponseDto> patientResponseDto = patientPage.getContent()
                    .stream()
                    .map(patient -> new PatientResponseDto(
                            patient.getId(),
                            patient.getPatientSerialID(),
                            patient.getFullName(),
                            patient.getNic(),
                            patient.getDob(),
                            patient.getGender(),
                            patient.getAddress(),
                            patient.getContactNo(),
                            patient.getEmail(),
                            medicalHistoryMapper.toMedicalHistoryDto(patient.getMedicalHistory()),
                            patient.getCreatedBy(),
                            patient.getCreatedDate(),
                            patient.getModifyBy(),
                            patient.getModifyDate()


                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponsePatientDto(
                    patientPage.getNumberOfElements(),
                    patientResponseDto,
                    patientPage.getTotalPages(),
                    patientPage.getTotalElements(),
                    patientPage.getNumber(),
                    patientPage.getSize(),
                    patientPage.hasNext(),
                    patientPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }



}