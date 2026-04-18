package com.edu.Institiute.repo;

import com.edu.Institiute.entity.RFQHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFQHeaderRepo extends JpaRepository<RFQHeader,Integer> {

}
