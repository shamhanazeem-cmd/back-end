package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportAppointmentRepo  extends JpaRepository<Appointment, String> {


    @Query(value = "SELECT d.doctor_name, COUNT(a.id) " +
            "FROM Doctor d " +
            "LEFT JOIN appointment a ON d.id = a.doctor_id " +
            "GROUP BY d.id, d.doctor_name",
            nativeQuery = true)
    List<Object[]> getAppointmentCountByAllDoctors();
}
