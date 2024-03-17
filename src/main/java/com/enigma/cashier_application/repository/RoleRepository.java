package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.constant.UserRole;
import com.enigma.cashier_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value ="SELECT * FROM m_role WHERE role= :role", nativeQuery = true)
    Optional<Role> findByRole(@Param("role") UserRole role);
}
