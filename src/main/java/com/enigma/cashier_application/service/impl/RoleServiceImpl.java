package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.constant.UserRole;
import com.enigma.cashier_application.entity.Role;
import com.enigma.cashier_application.repository.RoleRepository;
import com.enigma.cashier_application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role saveOrGet(UserRole role) {
        return roleRepository.findByRole(role).orElseGet(()->roleRepository.saveAndFlush(Role.builder().role(role).build()));
    }
}
