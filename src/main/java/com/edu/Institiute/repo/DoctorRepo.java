package com.edu.Institiute.repo;


import com.edu.Institiute.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor,Integer>{

    @Query(value = "SELECT * FROM Doctor WHERE id=:doctorId", nativeQuery = true)
    Doctor getAllDoctorsForProvidedId(@Param("doctorId") String doctorId);

    @Query(value = "SELECT * FROM Doctor WHERE id=:doctorId", nativeQuery = true)
    Optional<Doctor> getDoctorById(@Param("doctorId") String doctorId);

    @Query(value = "SELECT * FROM Doctor WHERE id=:doctorId", nativeQuery = true)
    List<Doctor> getDoctorDetailById(@Param("doctorId") String doctorId);

}
