package com.edu.Institiute.repo;

import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory,Integer> {



//    @Query(value = "SELECT * FROM medical_history WHERE id=mediId", nativeQuery = true)
//    Optional<MedicalHistory> findByMediHistoryId(Integer mediId);

    @Query(value = "SELECT * FROM medical_history WHERE id=:medicalHistoryId", nativeQuery = true)
    MedicalHistory getAllMedicalHistoryForProvidedId(@Param("medicalHistoryId") String medicalHistoryId);

    @Query(value = "SELECT * FROM medical_history WHERE id=:medicalHistoryId", nativeQuery = true)
    Optional<MedicalHistory> getMedicalHistoryById(@Param("medicalHistoryId") String medicalHistoryId);

    @Query(value = "SELECT * FROM medical_history WHERE id=:medicalHistoryId", nativeQuery = true)
    List<MedicalHistory>  getAllMedicalHistory(@Param("medicalHistoryId") String medicalHistoryId);


}
