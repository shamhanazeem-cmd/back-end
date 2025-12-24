package com.edu.Institiute.api;

import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.service.MedicalHistoryService;
import com.edu.Institiute.service.NotificationService;
import com.edu.Institiute.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/notification")

public class NotificationController {

    @Autowired
    private NotificationService NotificationService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public ResponseEntity<StandardResponse> saveNotification(@RequestBody RequestRegistryDto data){
        CommonResponseDto responseData = NotificationService.saveNotification(data);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{notificationId}")
    public ResponseEntity<StandardResponse> updateNotification(@RequestBody RequestRegistryDto data, @PathVariable String notificationId){
        CommonResponseDto responseData = NotificationService.updateNotification(data,notificationId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{notificationId}")
    public ResponseEntity<StandardResponse> deleteNotification(@PathVariable String notificationId){
        CommonResponseDto responseData = NotificationService.removeNotification(notificationId);
        return new ResponseEntity<>(
                new StandardResponse(
                        responseData.getCode(),
                        responseData.getMessage(),
                        responseData.getData()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{notificationId}")
    public ResponseEntity<StandardResponse> getNotification(@PathVariable String notificationId)throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Notification List",
                        NotificationService .notificationById(notificationId)),
                HttpStatus.OK
        );
    }


    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllNotifiaction()throws SQLException{
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Notifiaction  List",
                        NotificationService .allNotification()),
                HttpStatus.OK
        );
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public ResponseEntity<StandardResponse> getAllPagedNotifiaction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Notifiaction List",
                        NotificationService.getAllPagedNotification(page, size)),
                HttpStatus.OK
        );
    }

}
