package com.edu.Institiute.repo;



import com.edu.Institiute.entity.RFQHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface RFQHeaderRepo extends JpaRepository<RFQHeader,Integer> {

}
