package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value ="SELECT * FROM m_customer", nativeQuery = true)
    List<Customer> findAllCustomer();


    @Query(value ="SELECT * FROM m_customer WHERE customer_name = :name", nativeQuery = true)
    List<Customer> findAllByName(@Param("name") String name);

    @Modifying
    @Query(value= "DELETE FROM m_customer WHERE id= :id", nativeQuery = true)
    void deleteById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value ="UPDATE m_customer SET customer_name = :name, mobile_phone_no=:phone WHERE id = :id", nativeQuery = true)
    void updateCustomer(@Param("id") String id, @Param("name") String name, @Param("phone") String phone);

    @Modifying
    @Transactional
    @Query(value ="INSERT INTO m_customer (id, customer_name, member, mobile_phone_no, user_account_id) VALUES " +
            "(:id, :customerName, :member, :phone, :account)", nativeQuery = true)
    void create(@Param("id") String id, @Param("customerName") String name, @Param("member") Boolean member, @Param("phone") String phone, @Param("account") String account);
}
