package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.constant.UserRole;
import com.enigma.cashier_application.dto.request.AuthRequest;
import com.enigma.cashier_application.dto.response.LoginResponse;
import com.enigma.cashier_application.dto.response.RegisterResponse;
import com.enigma.cashier_application.entity.Customer;
import com.enigma.cashier_application.entity.Role;
import com.enigma.cashier_application.entity.UserAccount;
import com.enigma.cashier_application.repository.UserAccountRepository;
import com.enigma.cashier_application.service.AuthService;
import com.enigma.cashier_application.service.CustomerService;
import com.enigma.cashier_application.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Value("${wmb.username.superadmin}")
    private String usernameSuperAdmin;

    @Value("${wmb.password.superadmin}")
    private String passwordSuperAdmin;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void iniSuperAdmin(){
        Optional<UserAccount> superAdminAccount=userAccountRepository.findByUsername(usernameSuperAdmin);
        if (superAdminAccount.isPresent()) return;

        Role superAdmin = roleService.saveOrGet(UserRole.ROLE_SUPER_ADMIN);
        Role admin = roleService.saveOrGet(UserRole.ROLE_ADMIN);
        Role customer = roleService.saveOrGet(UserRole.ROLE_CUSTOMER);
        UserAccount account = UserAccount.builder()
                .username(usernameSuperAdmin)
                .password(passwordEncoder.encode(passwordSuperAdmin))
                .role(List.of(superAdmin, admin, customer))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(AuthRequest request) {
        Role role = roleService.saveOrGet(UserRole.ROLE_CUSTOMER);
        String hashPassword= passwordEncoder.encode(request.getPassword());
        UserAccount userAccount=UserAccount.builder()
                .id(UUID.randomUUID().toString())
                .role(List.of(role))
                .isEnable(true)
                .username(request.getUsername())
                .password(hashPassword)
                .build();
        userAccountRepository.create(userAccount.getId(), userAccount.getIsEnable(),
                userAccount.getPassword(), userAccount.getUsername());
        Customer customer=Customer.builder()
                .isMember(true)
                .id(UUID.randomUUID().toString())
                .userAccount(userAccount)
                .build();
        customerService.createCustomerAccount(customer.getId(), null, true, null, customer.getUserAccount().getId());
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(userAccount.getRole().stream().map(role1 -> role1.getRole().name()).toList())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleUser=roleService.saveOrGet(UserRole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.saveOrGet(UserRole.ROLE_ADMIN);
        String hashPassword= passwordEncoder.encode(request.getPassword());
        UserAccount userAccount=UserAccount.builder()
                .role(List.of(roleAdmin, roleUser))
                .isEnable(true)
                .username(request.getUsername())
                .password(hashPassword)
                .build();
        userAccountRepository.create(userAccount.getId(), userAccount.getIsEnable(),
                userAccount.getPassword(), userAccount.getUsername());
        Customer customer=Customer.builder()
                .isMember(true)
                .id(UUID.randomUUID().toString())
                .userAccount(userAccount)
                .build();
        customerService.createCustomerAccount(customer.getId(), null, true, null, customer.getUserAccount().getId());
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(userAccount.getRole().stream().map(role1 -> role1.getRole().name()).toList())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        return null;
    }
}
