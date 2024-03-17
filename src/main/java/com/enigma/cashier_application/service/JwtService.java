package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.response.JwtClaims;
import com.enigma.cashier_application.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount account);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
