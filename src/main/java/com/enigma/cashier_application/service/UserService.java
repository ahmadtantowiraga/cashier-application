package com.enigma.cashier_application.service;

import com.enigma.cashier_application.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getById(String id);
}
