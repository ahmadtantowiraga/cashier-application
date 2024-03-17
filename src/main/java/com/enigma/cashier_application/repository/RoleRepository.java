package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.constant.UserRole;
import com.enigma.cashier_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
