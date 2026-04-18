package com.edu.Institiute.repo;


import com.edu.Institiute.entity.RFQDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RFQDetailsRepo extends JpaRepository<RFQDetails,Integer> {

}
