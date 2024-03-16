package com.enigma.cashier_application.service;

import com.enigma.cashier_application.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    void createBulk(List<TransactionDetail> request);
}
