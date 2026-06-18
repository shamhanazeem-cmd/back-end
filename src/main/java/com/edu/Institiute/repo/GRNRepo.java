package com.edu.Institiute.repo;


import com.edu.Institiute.entity.GRNHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GRNRepo extends JpaRepository<GRNHeader,Integer> {
}
