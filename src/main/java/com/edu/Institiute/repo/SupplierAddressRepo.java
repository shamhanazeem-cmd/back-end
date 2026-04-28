package com.edu.Institiute.repo;

import com.edu.Institiute.entity.SupplierAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface SupplierAddressRepo extends JpaRepository<SupplierAddress,Integer> {

    @Query(value = "SELECT * FROM supplier_address WHERE id=:supplierAddressId", nativeQuery = true)
    Optional<SupplierAddress> getSupplierAddressById(@Param("supplierAddressId") String supplierAddressId);

    @Query(value = "SELECT * FROM supplier_address WHERE id=:supplierAddressId", nativeQuery = true)
    List<SupplierAddress> getSupplierAddressDetailById(@Param("supplierAddressId") String supplierAddressId);

}
