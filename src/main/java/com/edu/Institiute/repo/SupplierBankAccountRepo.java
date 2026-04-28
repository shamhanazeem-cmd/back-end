package com.edu.Institiute.repo;

import com.edu.Institiute.entity.SupplierBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface SupplierBankAccountRepo extends JpaRepository<SupplierBankAccount,Integer> {

    @Query(value = "SELECT * FROM supplier_bank_account WHERE id=:accountId", nativeQuery = true)
    Optional<SupplierBankAccount> getSupplierAccountById(@Param("accountId") String accountId);

    @Query(value = "SELECT * FROM supplier_bank_account WHERE id=:accountId", nativeQuery = true)
    List<SupplierBankAccount> getSupplierBankAccountDetailById(@Param("accountId") String accountId);

}
