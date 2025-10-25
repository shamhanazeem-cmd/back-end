package com.edu.Institiute.repo;


import com.edu.Institiute.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient,Integer>{
    
    @Query(value = "SELECT * FROM patient WHERE id=:patientId", nativeQuery = true)
    Patient getAllPatientForProvidedId (@Param("patientId") String patientId);

    @Query(value = "SELECT * FROM patient WHERE id=:patientId", nativeQuery = true)
    Optional<Patient>  getPatientById(@Param("patientId") String patientId);

    @Query(value = "SELECT * FROM patient WHERE id=:patientId", nativeQuery = true)
    List<Patient> getPatientDetailById(@Param("patientId") String patientId);

}
