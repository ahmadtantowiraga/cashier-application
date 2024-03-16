package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.TransactionDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    @Modifying
    @Transactional
    @Query(value ="INSERT INTO m_transaction_detail (id, qty, product_id, transaction_id) VALUES " +
                  "(:id, :qty, :productId, :transactionId)", nativeQuery = true)
    void create(@Param("id") String id, @Param("qty") Integer qty, @Param("productId") String productId, @Param("transactionId") String transactionId);
}
