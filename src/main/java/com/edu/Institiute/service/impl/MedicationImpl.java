package com.edu.Institiute.service.impl;


import com.edu.Institiute.dto.DoctorDto;
import com.edu.Institiute.dto.MedicationDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.DoctorResponseDto;

import com.edu.Institiute.dto.responseDto.MedicationResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseDoctorDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicationDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.DoctorService;
import com.edu.Institiute.service.MedicationService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicationImpl implements MedicationService {

    @Autowired
    private Generator generator;


    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private PrescriptionRepo prescriptionRepo;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private MedicationRepo medicationRepo;

    @Autowired
    private MedicationMapper medicationMapper;


    @Override
    public CommonResponseDto saveMedication(RequestRegistryDto dto) {
        try {
            int medicationId = generator.generateFourNumNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            Optional<Prescription> prescription = prescriptionRepo.findById(dto.getPrescription());

            MedicationDto medicationDto = new MedicationDto(
                    medicationId,
                    dto.getDrugName(),
                    dto.getDosage(),
                    dto.getDuration(),
                    dto.getInstructions(),
                    dto.getCreatedBy(),
                    dto.getCreatedDate(),
                    dto.getModifyBy(),
                    dto.getModifyDate(),
                    statusMapper.toStatusDto(status.get()),
                    prescriptionMapper.toPrescriptionDto(prescription.get())
            );

            medicationRepo.save(medicationMapper.dtoToMedicationEntity(medicationDto));

            return new CommonResponseDto(201, "Medication  saved!", medicationDto.getDrugName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updateMedication(RequestRegistryDto dto, String medicationId) {
        try {

            Medication allMedicationForProvidedId = medicationRepo.getAllMedicationForProvidedId(medicationId);
            allMedicationForProvidedId.setDrugName(dto.getDrugName());
            allMedicationForProvidedId.setDosage(dto.getDosage());
            allMedicationForProvidedId.setDuration(dto.getDuration());
            allMedicationForProvidedId.setInstructions(dto.getInstructions()
            );


            medicationRepo.save( allMedicationForProvidedId);
            return new CommonResponseDto(201, "Medication  Updated!",  allMedicationForProvidedId.getDrugName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeMedication(String medicationId) {
        Optional<Medication> medication = medicationRepo.getMedicationById(medicationId);


        if (medication.isPresent()) {
            medicationRepo.delete(medication.get());
            return new CommonResponseDto(201, "medication was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any medication...!");
        }
    }

    @Override
    public PaginatedResponseMedicationDto medicationById(String medicationId) throws SQLException {
        try {
            List<Medication> allMedicationForProvidedId = medicationRepo.getMedicationDetailById(medicationId);
            List<MedicationResponseDto> medicationResponseDto = new ArrayList<>();


            for (Medication r :allMedicationForProvidedId) {
                medicationResponseDto.add(
                        new MedicationResponseDto(
                                r.getId(),
                                r.getDrugName(),
                                r.getDosage(),
                                r.getDuration(),
                                r.getInstructions(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                prescriptionMapper.toPrescriptionDto(r.getPrescription())

                        )
                );
            }
            System.out.println(medicationResponseDto);
            return new PaginatedResponseMedicationDto(
                    medicationRepo.count(),
                    medicationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseMedicationDto allMedication() throws SQLException {
        try {
            List<Medication> allMedicationForProvidedId = medicationRepo.findAll();
            List<MedicationResponseDto> medicationResponseDto = new ArrayList<>();

            for (Medication r : allMedicationForProvidedId) {
                medicationResponseDto.add(
                        new MedicationResponseDto(
                                r.getId(),
                                r.getDrugName(),
                                r.getDosage(),
                                r.getDuration(),
                                r.getInstructions(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus()),
                                prescriptionMapper.toPrescriptionDto(r.getPrescription())


                        )
                );
            }
            return new PaginatedResponseMedicationDto(
                    medicationRepo.count(),
                    medicationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }


    @Override
    public PaginatedResponseMedicationDto getAllPagedMedication(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Medication> medicationPage = medicationRepo.findAll(pageable);

            List<MedicationResponseDto> medicationResponseDto = medicationPage.getContent()
                    .stream()
                    .map(medication -> new MedicationResponseDto(
                            medication.getId(),
                            medication.getDrugName(),
                            medication.getDosage(),
                            medication.getDuration(),
                            medication.getInstructions(),
                            medication.getCreatedBy(),
                            medication.getCreatedDate(),
                            medication.getModifyBy(),
                            medication.getModifyDate(),
                            statusMapper.toStatusDto(medication.getStatus()),
                            prescriptionMapper.toPrescriptionDto(medication.getPrescription())
                            )
                    )
                    .collect(Collectors.toList()); // Fixed this line

            return new PaginatedResponseMedicationDto(
                    medicationPage.getNumberOfElements(),
                    medicationResponseDto,
                    medicationPage.getTotalPages(),
                    medicationPage.getTotalElements(),
                    medicationPage.getNumber(),
                    medicationPage.getSize(),
                    medicationPage.hasNext(),
                    medicationPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }









}
