package com.edu.Institiute.repo;


import com.edu.Institiute.entity.SupplierQuotationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierQuotationDetailRepo extends JpaRepository<SupplierQuotationDetail,Integer> {
}
