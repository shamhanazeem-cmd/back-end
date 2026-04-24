package com.edu.Institiute.repo;

import com.edu.Institiute.entity.PurchaseOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailsRepo extends JpaRepository<PurchaseOrderDetails,Integer> {
}
