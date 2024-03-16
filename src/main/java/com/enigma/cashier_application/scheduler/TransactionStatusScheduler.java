package com.enigma.cashier_application.scheduler;

import com.enigma.cashier_application.entity.Transaction;
import com.enigma.cashier_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionStatusScheduler {
    private final TransactionService transactionService;

    @Scheduled(fixedRate = 1000*60*30)
    public void checkStatus(){
        log.info("START checkFailedPayments() : {}", System.currentTimeMillis());
        transactionService.updateStatusTransaction();
        log.info("END checkFailedPayments() : {}", System.currentTimeMillis());
    }
}
