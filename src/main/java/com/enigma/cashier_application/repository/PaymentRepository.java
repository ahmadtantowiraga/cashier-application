package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.Payment;
import com.enigma.cashier_application.entity.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query(value ="SELECT * FROM m_payment WHERE transaction_status= :status", nativeQuery = true)
    List<Payment> findAllByTransactionStatus(@Param("status") String status);
    @Modifying
    @Transactional
    @Query(value ="UPDATE m_payment SET transaction_status = :status WHERE id = :id", nativeQuery = true)
    void updateProduct(@Param("id") String id, @Param("status") String status);

}
