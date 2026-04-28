package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Doctor;
import com.edu.Institiute.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier,Integer>{
    @Query(value = "SELECT * FROM supplier WHERE id=:supplierId", nativeQuery = true)
    Optional<Supplier> getSupplierById(@Param("supplierId") String supplierId);

    @Query(value = "SELECT * FROM supplier WHERE id=:supplierId", nativeQuery = true)
    List<Supplier> getSupplierDetailById(@Param("supplierId") String supplierId);

}
