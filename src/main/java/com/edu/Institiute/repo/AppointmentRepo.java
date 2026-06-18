package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepo  extends JpaRepository<Appointment,Integer> {

    @Query(value = "SELECT * FROM appointment WHERE id=:appointmentId", nativeQuery = true)
    Appointment getAllAppointmentForProvidedId(@Param("appointmentId") String appointmentId);

    @Query(value = "SELECT * FROM appointment WHERE id=:appointmentId", nativeQuery = true)
    Optional<Appointment> getAppointmentById(@Param("appointmentId") String appointmentId);

    @Query(value = "SELECT * FROM appointment WHERE id=:appointmentId", nativeQuery = true)
    List<Appointment> getAllAppointment(@Param("appointmentId") String appointmentId);

    @Query(value = "SELECT * FROM appointment WHERE status_id=1", nativeQuery = true)
    List<Appointment> findByStatus(int id);


}
