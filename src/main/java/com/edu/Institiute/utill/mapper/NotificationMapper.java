package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.NotificationDto;

import com.edu.Institiute.entity.Notification;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper(componentModel = "spring")

public interface NotificationMapper {
    Notification dtoToNotificationEntity(NotificationDto notificationDto);
}
