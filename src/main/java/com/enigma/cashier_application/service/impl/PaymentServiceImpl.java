package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.repository.PaymentRepository;
import com.enigma.cashier_application.service.PaymentService;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public void update(String id, String status) {
        paymentRepository.updateProduct(id, status);
    }
}
