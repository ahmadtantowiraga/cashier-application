package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Modifying
    @Transactional
    @Query(value ="INSERT INTO m_user_account (id, is_enable, password, username) VALUES " +
            "(:id, :isEnable, :password, :username)", nativeQuery = true)
    void create(@Param("id") String id, @Param("isEnable") Boolean isEnable, @Param("password") String password, @Param("username") String username);

    @Query(value ="SELECT * FROM m_user_account WHERE username= :username", nativeQuery = true)
    Optional<UserAccount> findByUsername(@Param("username") String username);
}
