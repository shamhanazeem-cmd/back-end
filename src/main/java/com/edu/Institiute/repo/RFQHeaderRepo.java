package com.edu.Institiute.repo;

import com.edu.Institiute.entity.RFQHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RFQHeaderRepo extends JpaRepository<RFQHeader,Integer> {

    @Query(value = "SELECT * FROM rfq_header WHERE id=:rfqId", nativeQuery = true)
    Optional<RFQHeader> findRfqById(@Param("rfqId") Integer rfqId);

    @Query(value = "SELECT * FROM rfq_header WHERE id = :rfqId", nativeQuery = true)
    Optional<RFQHeader> getAllRFQsForProvidedId(@Param("rfqId") Integer rfqId);
}
