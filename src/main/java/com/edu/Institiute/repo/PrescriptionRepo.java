package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Appointment;
import com.edu.Institiute.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepo extends JpaRepository<Prescription,Integer> {

    @Query(value = "SELECT * FROM prescription WHERE id=:prescriptionId", nativeQuery = true)
    Prescription getAllPrescriptionForProvidedId(@Param("prescriptionId") String prescriptionId);

    @Query(value = "SELECT * FROM prescription WHERE id=:prescriptionId", nativeQuery = true)
    Optional<Prescription> getPrescriptionById(@Param("prescriptionId") String prescriptionId);

    @Query(value = "SELECT * FROM prescription WHERE id=:prescriptionId", nativeQuery = true)
    List<Prescription> getAllPrescription(@Param("prescriptionId") String prescriptionId);


}
