package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.request.AuthRequest;
import com.enigma.cashier_application.dto.response.LoginResponse;
import com.enigma.cashier_application.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
