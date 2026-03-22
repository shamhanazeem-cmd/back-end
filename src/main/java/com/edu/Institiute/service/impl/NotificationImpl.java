package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.NotificationDto;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.NotificationResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseNotificationDto;
import com.edu.Institiute.entity.*;

import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.NotificationService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class NotificationImpl implements NotificationService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public CommonResponseDto saveNotification(RequestRegistryDto dto) {
        try {
            int notificationId = generator.generateFourNumNumbers();
            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());
            Optional<Appointment> appointment = appointmentRepo.findById(dto.getAppointment());

            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : dto.getCreatedBy();


            NotificationDto notificationDto = new NotificationDto(
                    notificationId,
                    dto.getSentDate(),
                    dto.getChannel(),
                    createdBy,
                    new Date(),
                    "",
                    null,
                    appointmentMapper.toAppointmentDto(appointment.get()),
                    statusMapper.toStatusDto(status.get())

            );

            notificationRepo.save(notificationMapper.dtoToNotificationEntity(notificationDto));

            return new CommonResponseDto(201, "Notification  saved!", notificationDto.getSentDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }


    @Override
    public CommonResponseDto updateNotification(RequestRegistryDto dto, String notificationId) {
        try {

            Notification allNotificationForProvidedId = notificationRepo.getAllNotificationForProvidedId(notificationId);
            allNotificationForProvidedId.setSentDate(dto.getSentDate());
            allNotificationForProvidedId.setChannel(dto.getChannel());


            notificationRepo.save( allNotificationForProvidedId);
            return new CommonResponseDto(201, "Notification  Updated!",  allNotificationForProvidedId.getSentDate(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto removeNotification(String notificationId) {
        Optional<Notification> notification = notificationRepo.getNotificationById(notificationId);


        if (notification.isPresent()) {
            notificationRepo.delete(notification.get());
            return new CommonResponseDto(201, "notification was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any notification...!");
        }
    }

    @Override
    public PaginatedResponseNotificationDto notificationById(String notificationId) throws SQLException {
        try {
            List<Notification> allNotificationForProvidedId = notificationRepo.getNotificationDetailById(notificationId);
            List<NotificationResponseDto> notificationResponseDto = new ArrayList<>();


            for (Notification r :allNotificationForProvidedId) {
                notificationResponseDto.add(
                        new NotificationResponseDto(
                                r.getId(),
                                r.getSentDate(),
                                r.getChannel(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                appointmentMapper.toAppointmentDto(r.getAppointment()),
                                statusMapper.toStatusDto(r.getStatus())


                        )
                );
            }
            System.out.println(notificationResponseDto);
            return new PaginatedResponseNotificationDto(
                    notificationRepo.count(),
                    notificationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }



    @Override
    public PaginatedResponseNotificationDto allNotification() throws SQLException {
        try {
            List<Notification> allNotificationForProvidedId = notificationRepo.findAll();
            List<NotificationResponseDto> notificationResponseDto = new ArrayList<>();

            for (Notification r : allNotificationForProvidedId) {
                notificationResponseDto.add(
                        new NotificationResponseDto(
                                r.getId(),
                                r.getSentDate(),
                                r.getChannel(),
                                r.getCreatedBy(),
                                r.getCreatedDate(),
                                r.getModifyBy(),
                                r.getModifyDate(),
                                appointmentMapper.toAppointmentDto(r.getAppointment()),
                                statusMapper.toStatusDto(r.getStatus())

                        )
                );
            }
            return new PaginatedResponseNotificationDto(
                    notificationRepo.count(),
                    notificationResponseDto
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }


    @Override
    public PaginatedResponseNotificationDto getAllPagedNotification(int page, int size) throws SQLException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Notification> notificationPage = notificationRepo.findAll(pageable);

            List<NotificationResponseDto> notificationResponseDto= notificationPage.getContent()
                    .stream()
                    .map(notification -> new NotificationResponseDto(
                            notification.getId(),
                            notification.getSentDate(),
                            notification.getChannel(),
                            notification.getCreatedBy(),
                            notification.getCreatedDate(),
                            notification.getModifyBy(),
                            notification.getModifyDate(),
                            appointmentMapper.toAppointmentDto(notification.getAppointment()),
                            statusMapper.toStatusDto(notification.getStatus())
                            )
                    )
                    .collect(Collectors.toList()); // Fixed this line

            return new PaginatedResponseNotificationDto(
                    notificationPage.getNumberOfElements(),
                    notificationResponseDto,
                    notificationPage.getTotalPages(),
                    notificationPage.getTotalElements(),
                    notificationPage.getNumber(),
                    notificationPage.getSize(),
                    notificationPage.hasNext(),
                    notificationPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }









}
