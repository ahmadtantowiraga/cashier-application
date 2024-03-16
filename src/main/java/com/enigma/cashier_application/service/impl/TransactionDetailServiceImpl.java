package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.entity.TransactionDetail;
import com.enigma.cashier_application.repository.TransactionDetailRepository;
import com.enigma.cashier_application.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;

    @Override
    public void createBulk(List<TransactionDetail> request) {
        request.stream().forEach(transactionDetail -> {
            transactionDetailRepository.create(transactionDetail.getId(), transactionDetail.getQty(), transactionDetail.getProduct().getId(), transactionDetail.getTransaction().getId());
        });

    }
}
