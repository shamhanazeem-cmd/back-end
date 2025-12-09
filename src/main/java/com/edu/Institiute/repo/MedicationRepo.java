package com.edu.Institiute.repo;



import com.edu.Institiute.entity.Medication;
import com.edu.Institiute.entity.Specialization;
import com.edu.Institiute.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicationRepo extends JpaRepository<Medication,Integer> {

    @Query(value = "SELECT * FROM medication WHERE id=:medicationId", nativeQuery = true)
    Medication getAllMedicationForProvidedId(@Param("medicationId") String medicationId);

    @Query(value = "SELECT * FROM medication WHERE id=:medicationId", nativeQuery = true)
    Optional<Medication> getMedicationById(@Param("medicationId") String medicationId);

    @Query(value = "SELECT * FROM medication WHERE id=:medicationId", nativeQuery = true)
    List<Medication> getMedicationDetailById(@Param("medicationId") String medicationId);

}
