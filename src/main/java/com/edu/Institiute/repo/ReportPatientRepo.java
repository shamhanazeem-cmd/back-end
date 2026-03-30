package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPatientRepo extends JpaRepository<Patient, String> {

    @Query(value = "SELECT COUNT(*) FROM patient", nativeQuery = true)
    Long countAllPatient();

    @Query(value = "SELECT MONTH(customer_created_date) AS month, COUNT(*) AS count " +
            "FROM patient " +
            "WHERE created_date BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(created_date)", nativeQuery = true)
    List<Object[]> getPatientByMonth(@Param("startDate") String startDate,
                                       @Param("endDate") String endDate);
}
