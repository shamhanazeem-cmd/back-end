package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Specialization;
import com.edu.Institiute.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepo extends JpaRepository<Specialization,Integer> {

    @Query(value = "SELECT * FROM speacialization WHERE id=:specializationID", nativeQuery = true)
    Specialization getAllSpecializationForProvidedId(@Param("specializationID") String specializationID);

    @Query(value = "SELECT * FROM speacialization WHERE id=:specializationID", nativeQuery = true)
    Optional<Specialization> getSpecializationById(@Param("specializationID")String specializationID);

    @Query(value = "SELECT * FROM speacialization WHERE id=:specializationID", nativeQuery = true)
    List<Specialization> getSplById(@Param("specializationID")String specializationID);


    @Query(value = "SELECT * FROM speacialization WHERE id=?1", nativeQuery = true)
    Optional<Specialization> findSpecializationById(String specializationID);
}
