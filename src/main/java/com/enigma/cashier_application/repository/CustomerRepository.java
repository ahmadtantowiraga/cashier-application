package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Modifying
    @Query(value ="SELECT * FROM m_customer", nativeQuery = true)
    List<Customer> findAllCustomer();

    @Modifying
    @Query(value ="SELECT * FROM m_customer WHERE customer_name = :name", nativeQuery = true)
    List<Customer> findAllByName(@Param("name") String name);

    @Modifying
    @Query(value= "DELETE FROM m_customer WHERE id= :id", nativeQuery = true)
    void deleteById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value ="UPDATE m_customer SET customer_name = :name, mobile_phone_no=:phone WHERE id = :id", nativeQuery = true)
    void updateProduct(@Param("id") String id, @Param("name") String name, @Param("phone") String phone);


}
