package com.enigma.cashier_application.service;

import com.enigma.cashier_application.constant.UserRole;
import com.enigma.cashier_application.entity.Role;

public interface RoleService {
    Role saveOrGet(UserRole role);
}
