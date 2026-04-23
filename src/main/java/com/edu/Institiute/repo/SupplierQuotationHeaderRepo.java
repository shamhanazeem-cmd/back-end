package com.edu.Institiute.repo;

import com.edu.Institiute.entity.SupplierQuotationHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierQuotationHeaderRepo extends JpaRepository<SupplierQuotationHeader,Integer>{
    @Query(value = "SELECT * FROM supplier_quotation_header WHERE id=:id", nativeQuery = true)
    Optional<SupplierQuotationHeader> findSQById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM supplier_quotation_header WHERE id=:id", nativeQuery = true)
    Optional<SupplierQuotationHeader> getAllSupplierQoutationForProvidedId(@Param("id") Integer id);

}
