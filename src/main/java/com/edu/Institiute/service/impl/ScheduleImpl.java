package com.edu.Institiute.service.impl;


import com.edu.Institiute.dto.MedicalHistoryDto;
import com.edu.Institiute.dto.ScheduleDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;


import com.edu.Institiute.dto.responseDto.MedicalHistoryResponseDto;
import com.edu.Institiute.dto.responseDto.ScheduleResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseMedicalHistoryDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseScheduleDto;
import com.edu.Institiute.entity.Doctor;
import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Schedule;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.DoctorRepo;
import com.edu.Institiute.repo.ScheduleRepo;

import com.edu.Institiute.service.ScheduleService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.DoctorMapper;
import com.edu.Institiute.utill.mapper.ScheduleMapper;

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
public class ScheduleImpl implements ScheduleService {

    @Autowired
    private Generator generator;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ScheduleMapper scheduleMapper;


    @Override
    public CommonResponseDto saveSchedule(RequestRegistryDto dto) {
        try {
            int scheduleId = generator.generateFourNumNumbers();
            Optional<Doctor> doctor = doctorRepo.getDoctorById(dto.getDoctor());

            ScheduleDto scheduleDto = new ScheduleDto(
                    scheduleId,
                    dto.getDayOfWeek(),
                    dto.getStartTime(),
                    dto.getEndTime(),
                    dto.getSlotDuration(),
                    dto.getMaxPatients(),
                    dto.getCreatedBy(),
                    dto.getCreatedDate(),
                    dto.getModifyBy(),
                    dto.getModifyDate(),
                    doctorMapper.toDoctorDto(doctor.get())

            );
            scheduleRepo.save(scheduleMapper.dtoToScheduleEntity(scheduleDto));

            return new CommonResponseDto(201, "Schedule  saved!", scheduleDto.getDayOfWeek(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }

    }

    @Override
    public CommonResponseDto updateSchedule(RequestRegistryDto dto, String scheduleId) {
        try {

            Schedule allScheduleForProvidedId = scheduleRepo.getallScheduleForProvidedId(scheduleId);
            allScheduleForProvidedId.setDayOfWeek(dto.getDayOfWeek());
            allScheduleForProvidedId.setStartTime(dto.getStartTime());
            allScheduleForProvidedId.setEndTime(dto.getEndTime());
            allScheduleForProvidedId.setSlotDuration(dto.getSlotDuration());
            allScheduleForProvidedId.setMaxPatients(dto.getMaxPatients());


            scheduleRepo.save(allScheduleForProvidedId);
            return new CommonResponseDto(201, "Schedule  Updated!", allScheduleForProvidedId.getDayOfWeek(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }


        @Override
        public CommonResponseDto removeSchedule(String scheduleId) {
            Optional<Schedule> schedule = scheduleRepo.scheduleById(scheduleId);


            if (schedule.isPresent()) {
                scheduleRepo.delete(schedule.get());
                return new CommonResponseDto(201, "schedule was deleted!", true, new ArrayList<>());
            } else {
                throw new EntryNotFoundException("Can't find any schedule...!");
            }
        }

    @Override
    public PaginatedResponseScheduleDto scheduleById(String scheduleId) throws SQLException {
        try {
            List<Schedule> allScheduleForProvidedId = scheduleRepo.getAllSchedule(scheduleId);
            List<ScheduleResponseDto> scheduleResponseDto = new ArrayList<>();


            for (Schedule r :allScheduleForProvidedId) {
                scheduleResponseDto.add(
                        new ScheduleResponseDto(
                                r.getId(),
                                r.getDayOfWeek(),
                                r.getStartTime(),
                                r.getEndTime(),
                                r.getSlotDuration(),
                                r.getMaxPatients(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctor())
                        )
                            );
            }
            System.out.println(scheduleResponseDto);
            return new PaginatedResponseScheduleDto(
                    scheduleRepo.count(),
                    scheduleResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseScheduleDto allSchedule() throws SQLException {
        try {
            List<Schedule> allScheduleForProvidedId = scheduleRepo.findAll();
            List<ScheduleResponseDto> scheduleResponseDto = new ArrayList<>();

            for (Schedule r :allScheduleForProvidedId) {
                scheduleResponseDto.add(
                        new ScheduleResponseDto(
                                r.getId(),
                                r.getDayOfWeek(),
                                r.getStartTime(),
                                r.getEndTime(),
                                r.getSlotDuration(),
                                r.getMaxPatients(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                doctorMapper.toDoctorDto(r.getDoctor())
                        )
                );
            }
            return new PaginatedResponseScheduleDto(
                    scheduleRepo.count(),
                    scheduleResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }


    @Override
    public PaginatedResponseScheduleDto getAllPagedSchedule(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Schedule> schedulePage = scheduleRepo.findAll(pageable);

            List<ScheduleResponseDto>  scheduleResponseDto = schedulePage.getContent()
                    .stream()
                    .map(schedule -> new ScheduleResponseDto(
                            schedule.getId(),
                            schedule.getDayOfWeek(),
                            schedule.getStartTime(),
                            schedule.getEndTime(),
                            schedule.getSlotDuration(),
                            schedule.getMaxPatients(),
                            schedule.getCreatedBy(),
                            schedule.getCreatedDate(),
                            schedule.getModifyBy(),
                            schedule.getModifyDate(),
                            doctorMapper.toDoctorDto(schedule.getDoctor()
                            )
                    ))
                    .collect(Collectors.toList());

            return new PaginatedResponseScheduleDto(
                    schedulePage.getNumberOfElements(),
                    scheduleResponseDto,
                    schedulePage.getTotalPages(),
                    schedulePage.getTotalElements(),
                    schedulePage.getNumber(),
                    schedulePage.getSize(),
                    schedulePage.hasNext(),
                    schedulePage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }

}
