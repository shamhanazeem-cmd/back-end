package com.edu.Institiute.service.impl;

import com.edu.Institiute.dto.MedicalHistoryDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.MedicalHistoryResponseDto;

import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicalHistoryDto;
import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Status;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.MedicalHistoryRepo;
import com.edu.Institiute.repo.StatusRepo;
import com.edu.Institiute.service.MedicalHistoryService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.MedicalHistoryMapper;
import com.edu.Institiute.utill.mapper.StatusMapper;
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
public class MedicalHistoryImpl implements MedicalHistoryService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;


    @Override
    public CommonResponseDto saveMedical(RequestRegistryDto dto) {
        try {
            int medicalHistoryId = generator.generateFourNumNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());

            MedicalHistoryDto medicalHistoryDto = new MedicalHistoryDto(
                    medicalHistoryId,
                    dto.getAllergies(),
                    dto.getPastSurgeries(),
                    dto.getChronicConditions(),
                    dto.getMedicalHistory(),
                    dto.getCreatedBy(),
                    dto.getCreatedDate(),
                    dto.getModifyBy(),
                    dto.getModifyDate(),
                    statusMapper.toStatusDto(status.get())

            );
            medicalHistoryRepo.save(medicalHistoryMapper.dtoToMedicalHistoryEntity(medicalHistoryDto));

            return new CommonResponseDto(201, "Medical History  saved!", medicalHistoryDto.getAllergies(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updateMedical(RequestRegistryDto dto, String medicalHistoryId) {
        try {

            MedicalHistory allMedicalHistoryForProvidedId = medicalHistoryRepo.getAllMedicalHistoryForProvidedId(medicalHistoryId);
            allMedicalHistoryForProvidedId.setAllergies(dto.getAllergies());
            allMedicalHistoryForProvidedId.setPastSurgeries(dto.getPastSurgeries());
            allMedicalHistoryForProvidedId.setChronicConditions(dto.getChronicConditions());
            allMedicalHistoryForProvidedId.setMedicalHistory(dto.getMedicalHistory());

            medicalHistoryRepo.save( allMedicalHistoryForProvidedId);
            return new CommonResponseDto(201, "MedicalHistory  Updated!",  allMedicalHistoryForProvidedId.getAllergies(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeMedical(String medicalHistoryId) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepo.getMedicalHistoryById(medicalHistoryId);



        if (medicalHistory.isPresent()) {
            medicalHistoryRepo.delete(medicalHistory.get());
            return new CommonResponseDto(201, "MedicalHistory was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any MedicalHistory...!");
        }
    }

    @Override
    public PaginatedResponseMedicalHistoryDto medicalHistoryById(String medicalHistoryId) throws SQLException {
        try {
            List<MedicalHistory> allMedicalHistoryForProvidedId = medicalHistoryRepo.getAllMedicalHistory(medicalHistoryId);
            List<MedicalHistoryResponseDto> medicalHistoryResponseDto = new ArrayList<>();


            for (MedicalHistory r :allMedicalHistoryForProvidedId) {
                medicalHistoryResponseDto.add(
                        new MedicalHistoryResponseDto(
                                r.getId(),
                                r.getAllergies(),
                                r.getPastSurgeries(),
                                r.getChronicConditions(),
                                r.getMedicalHistory(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            System.out.println(medicalHistoryResponseDto);
            return new PaginatedResponseMedicalHistoryDto(
                    medicalHistoryRepo.count(),
                    medicalHistoryResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseMedicalHistoryDto allMedicalHistory() throws SQLException {
        try {
            List<MedicalHistory> allMedicalHistoryForProvidedId = medicalHistoryRepo.findAll();
            List<MedicalHistoryResponseDto> medicalHistoryResponseDto = new ArrayList<>();

            for (MedicalHistory r : allMedicalHistoryForProvidedId) {
                medicalHistoryResponseDto.add(
                        new MedicalHistoryResponseDto(
                                r.getId(),
                                r.getAllergies(),
                                r.getPastSurgeries(),
                                r.getChronicConditions(),
                                r.getMedicalHistory(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                statusMapper.toStatusDto(r.getStatus())
                        )
                );
            }
            return new PaginatedResponseMedicalHistoryDto(
                    medicalHistoryRepo.count(),
                    medicalHistoryResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseMedicalHistoryDto getAllPagedMedical(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MedicalHistory> medicalHistoryPage = medicalHistoryRepo.findAll(pageable);

            List<MedicalHistoryResponseDto> medicalHistoryResponseDto = medicalHistoryPage.getContent()
                    .stream()
                    .map(medicalHistory -> new MedicalHistoryResponseDto(
                            medicalHistory.getId(),
                            medicalHistory.getAllergies(),
                            medicalHistory.getPastSurgeries(),
                            medicalHistory.getChronicConditions(),
                            medicalHistory.getMedicalHistory(),
                            medicalHistory.getCreatedBy(),
                            medicalHistory.getCreatedDate(),
                            medicalHistory.getModifyBy(),
                            medicalHistory.getModifyDate(),
                            statusMapper.toStatusDto(medicalHistory.getStatus()
                      )
                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponseMedicalHistoryDto(
                    medicalHistoryPage.getNumberOfElements(),
                    medicalHistoryResponseDto,
                    medicalHistoryPage.getTotalPages(),
                    medicalHistoryPage.getTotalElements(),
                    medicalHistoryPage.getNumber(),
                    medicalHistoryPage.getSize(),
                    medicalHistoryPage.hasNext(),
                    medicalHistoryPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }
}
