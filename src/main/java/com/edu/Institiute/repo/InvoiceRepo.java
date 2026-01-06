package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface InvoiceRepo extends JpaRepository<Invoice,Integer> {

    @Query(value = "SELECT * FROM invoice WHERE id=:invoiceId", nativeQuery = true)
    Invoice getAllInvoiceForProvidedId(@Param("invoiceId") String invoiceId);

    @Query(value = "SELECT * FROM invoice WHERE id=:invoiceId", nativeQuery = true)
    Optional<Invoice> getInvoiceById(@Param("invoiceId") String invoiceId);

    @Query(value = "SELECT * FROM invoice WHERE id=:invoiceId", nativeQuery = true)
    List<Invoice> getAllInvoice(@Param("invoiceId") String invoiceId);



}
