package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.request.TransactionRequest;
import com.enigma.cashier_application.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    TransactionResponse findById(String id);
    Page<TransactionResponse> findAll(SearchRequest request);
    void updateStatusTransaction();
}
