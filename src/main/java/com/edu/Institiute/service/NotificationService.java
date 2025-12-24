package com.edu.Institiute.service;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseNotificationDto;

import java.sql.SQLException;

public interface NotificationService {
    CommonResponseDto saveNotification(RequestRegistryDto data);

    CommonResponseDto updateNotification(RequestRegistryDto data, String notificationId);

    CommonResponseDto removeNotification(String notificationId);

    PaginatedResponseNotificationDto notificationById(String notificationId) throws SQLException;

    PaginatedResponseNotificationDto allNotification() throws SQLException;

    PaginatedResponseNotificationDto getAllPagedNotification(int page, int size)throws SQLException;

}
