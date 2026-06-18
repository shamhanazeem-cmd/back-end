package com.edu.Institiute.repo;

import com.edu.Institiute.entity.PurchaseOrderHeaader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderHeaderRepo extends JpaRepository<PurchaseOrderHeaader,Integer> {
}
