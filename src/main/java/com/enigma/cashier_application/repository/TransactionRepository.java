package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.Product;
import com.enigma.cashier_application.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Modifying
    @Transactional
    @Query(value ="INSERT INTO m_transaction (id, transaction_date, customer_id) VALUES " +
                  "(:id, :transactionDate, :customerId)", nativeQuery = true)
    void create(@Param("id") String id, @Param("transactionDate") Date date, @Param("customerId") String customerId);

    @Query(value= "SELECT * FROM m_transaction", nativeQuery = true)
    List<Transaction> findAllTransaction();
}
