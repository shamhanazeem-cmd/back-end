package com.edu.Institiute.repo;

import com.edu.Institiute.entity.PurchaseOrderHeaader;
import com.edu.Institiute.entity.RFQHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderHeaderRepo extends JpaRepository<PurchaseOrderHeaader,Integer> {

    @Query(value = "SELECT * FROM purchase_order_header WHERE id=:id", nativeQuery = true)
    Optional<PurchaseOrderHeaader> findPoById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM purchase_order_header WHERE id=:id", nativeQuery = true)
    Optional<PurchaseOrderHeaader> getAllRFQsForProvidedId(@Param("id") Integer id);
}
