package com.edu.Institiute.utill.mapper;
import com.edu.Institiute.dto.AppointmentDto;
import com.edu.Institiute.entity.Appointment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment dtoToAppointmentEntity(AppointmentDto appointmentDto);

    AppointmentDto toAppointmentDto(Appointment appointment);

}
