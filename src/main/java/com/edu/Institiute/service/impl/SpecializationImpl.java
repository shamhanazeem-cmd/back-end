
package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.SpecializationDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;

import com.edu.Institiute.dto.responseDto.SpecializationResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSpecializationDto;
import com.edu.Institiute.entity.Specialization;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.SpecializationRepo;
import com.edu.Institiute.service.SpecializationService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.SpecializationMapper;
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
public class SpecializationImpl implements SpecializationService {

    @Autowired
    private Generator generator;

    @Autowired
    private SpecializationRepo specializationRepo;

    @Autowired
    private SpecializationMapper specializationMapper;


    @Override
    public CommonResponseDto saveSpecialization(RequestRegistryDto dto) {
        try {
            int specializationID = generator.generateFourNumNumbers();

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();

            SpecializationDto specializationDto = new SpecializationDto(
                    specializationID,
                    dto.getName(),
                    dto.getDescription(),
                    createdBy,
                    new Date(),
                    "",
                    null

            );
            specializationRepo.save(specializationMapper.dtoToSpecializationEntity(specializationDto));

            return new CommonResponseDto(201, " Specialization  saved!", specializationDto.getName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updateSpecialization(RequestRegistryDto dto, String specializationID) {
        try {

            Specialization allSpecializationForProvidedId = specializationRepo.getAllSpecializationForProvidedId(specializationID);
            allSpecializationForProvidedId.setName(dto.getAllergies());
            allSpecializationForProvidedId.setDescription(dto.getPastSurgeries());


            specializationRepo.save( allSpecializationForProvidedId);
            return new CommonResponseDto(201, " Specialization  Updated!",  allSpecializationForProvidedId.getName(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }


    @Override
    public CommonResponseDto removeSpecialization(String specializationID) {
        Optional<Specialization> specialization = specializationRepo.getSpecializationById(specializationID);



        if (specialization.isPresent()) {
            specializationRepo.delete(specialization.get());
            return new CommonResponseDto(201, "Specialization was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any Specialization...!");
        }
    }

    @Override
    public PaginatedResponseSpecializationDto specializationById(String specializationID) throws SQLException {
        try {
            List<Specialization> allSpecializationForProvidedId = specializationRepo.getSplById(specializationID);
            System.out.println("Data 1 object " + allSpecializationForProvidedId);
            List<SpecializationResponseDto> specializationResponseDto = new ArrayList<>();


            for (Specialization r :allSpecializationForProvidedId) {
                specializationResponseDto.add(
                        new SpecializationResponseDto(
                                r.getId(),
                                r.getName(),
                                r.getDescription(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()
                        )
                );
            }

            System.out.println("Data object " + specializationResponseDto);
            return new PaginatedResponseSpecializationDto(
                    specializationRepo.count(),
                    specializationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseSpecializationDto allSpecialization() throws SQLException {
        try {
            List<Specialization> allSpecializationForProvidedId = specializationRepo.findAll();
            List<SpecializationResponseDto> specializationResponseDto = new ArrayList<>();

            for (Specialization r : allSpecializationForProvidedId) {
                specializationResponseDto.add(
                        new SpecializationResponseDto(
                                r.getId(),
                                r.getName(),
                                r.getDescription(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate()

                        )
                );
            }
            return new PaginatedResponseSpecializationDto(
                    specializationRepo.count(),
                    specializationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseSpecializationDto getAllPagedSpecialization(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Specialization> specializationPage = specializationRepo.findAll(pageable);

            List<SpecializationResponseDto> specializationResponseDto = specializationPage.getContent()
                    .stream()
                    .map(specialization -> new SpecializationResponseDto(
                            specialization.getId(),
                            specialization.getName(),
                            specialization.getDescription(),
                            specialization.getCreatedBy(),
                            specialization.getCreatedDate(),
                            specialization.getModifyBy(),
                            specialization.getModifyDate()
                            )
                    )
                    .collect(Collectors.toList());

            return new PaginatedResponseSpecializationDto(
                    specializationPage.getNumberOfElements(),
                    specializationResponseDto,
                    specializationPage.getTotalPages(),
                    specializationPage.getTotalElements(),
                    specializationPage.getNumber(),
                    specializationPage.getSize(),
                    specializationPage.hasNext(),
                    specializationPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }

}

