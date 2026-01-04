package com.edu.Institiute.repo;


import com.edu.Institiute.entity.Patient;
import com.edu.Institiute.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {

    @Query(value = "SELECT * FROM Payment WHERE id=:paymentId", nativeQuery = true)
    Payment getAllPaymentForProvidedId (@Param("paymentId") String paymentId);

    @Query(value = "SELECT * FROM Payment WHERE id=:paymentId", nativeQuery = true)
    Optional<Payment> getPaymentById (@Param("paymentId") String paymentId);

    @Query(value = "SELECT * FROM Payment WHERE id=:paymentId", nativeQuery = true)
    List<Payment> getAllpayment (@Param("paymentId") String paymentId);

}
