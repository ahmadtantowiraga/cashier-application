package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.request.TransactionRequest;
import com.enigma.cashier_application.dto.response.CustomerResponse;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.dto.response.TransactionDetailResponse;
import com.enigma.cashier_application.dto.response.TransactionResponse;
import com.enigma.cashier_application.entity.Payment;
import com.enigma.cashier_application.entity.Product;
import com.enigma.cashier_application.entity.Transaction;
import com.enigma.cashier_application.entity.TransactionDetail;
import com.enigma.cashier_application.repository.PaymentRepository;
import com.enigma.cashier_application.repository.TransactionRepository;
import com.enigma.cashier_application.service.CustomerService;
import com.enigma.cashier_application.service.ProductService;
import com.enigma.cashier_application.service.TransactionDetailService;
import com.enigma.cashier_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final TransactionDetailService transactionDetailService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse create(TransactionRequest request) {
        String transactionId= UUID.randomUUID().toString();

        transactionRepository.create(transactionId, new Date(), request.getCustomerId());

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"));

        List<TransactionDetail> transactionDetailList = request.getTransactionDetail().stream().map(transactionDetailRequest -> {
            String transactionDetailId = UUID.randomUUID().toString();

            ProductResponse productResponse = productService.findById(transactionDetailRequest.getProductId());

            Product product = Product.builder().price(productResponse.getPrice())
                    .stock(productResponse.getStock() - transactionDetailRequest.getQty())
                    .id(transactionDetailRequest.getProductId())
                    .menuName(productResponse.getMenuName())
                    .build();
            productService.update(product);

            return TransactionDetail.builder()
                    .transaction(transaction)
                    .qty(transactionDetailRequest.getQty())
                    .id(transactionDetailId)
                    .product(product)
                    .build();
        }).toList();
        transactionDetailService.createBulk(transactionDetailList);

        transaction.setTransactionDetail(transactionDetailList);

        Payment payment = Payment.builder()
                .transactionStatus("ordered")
                .transaction(transaction)
                .build();

        paymentRepository.saveAndFlush(payment);

        transaction.setPayment(payment);


        return convertTransacionToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse findById(String id) {
        Transaction transaction=transactionRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction not found"));
        return convertTransacionToTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> findAll(SearchRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        List<Transaction> allTransaction = transactionRepository.findAllTransaction();
        List<TransactionResponse> transactionResponseList = allTransaction.stream().map(transaction -> {
            return TransactionResponse.builder()
                    .id(transaction.getId())
                    .date(transaction.getDate())
                    .transactionDetail(transaction.getTransactionDetail().stream().map(this::convertTransacionDetailToTransactionDetailResponse).toList())
                    .customerId(transaction.getCustomer().getId())
                    .totalPrice(transaction.getTransactionDetail().stream().mapToLong(transactionDetail ->
                                    (transactionDetail.getQty() * transactionDetail.getProduct().getPrice()))
                            .reduce(0, Long::sum))
                    .transactionStatus(transaction.getPayment().getTransactionStatus())
                    .build();
        }).toList();
        return  new PageImpl<>(transactionResponseList, pageable, transactionResponseList.size());
    }

    @Override
    public void updateStatusTransaction() {
        List<Payment> payments = paymentRepository.findAll();
        payments.forEach(payment -> {
            if (payment.getTransactionStatus().equals("ordered")){
                if((new Date()).getTime()-payment.getTransaction().getDate().getTime()>=1000*60*30){
                    paymentRepository.updateProduct(payment.getId(), "Paid");
                }
            }
        });
    }

    private TransactionResponse convertTransacionToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionDetail(transaction.getTransactionDetail().stream().map(this::convertTransacionDetailToTransactionDetailResponse).toList())
                .customerId(transaction.getCustomer().getId())
                .totalPrice(transaction.getTransactionDetail().stream().mapToLong(transactionDetail ->
                        (transactionDetail.getQty()*transactionDetail.getProduct().getPrice()))
                        .reduce(0, Long::sum))
                .id(transaction.getId())
                .date(transaction.getDate())
                .transactionStatus(transaction.getPayment().getTransactionStatus())
                .build();
    }
    private TransactionDetailResponse convertTransacionDetailToTransactionDetailResponse(TransactionDetail transactionDetail) {
        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .price(transactionDetail.getProduct().getPrice())
                .productName(transactionDetail.getProduct().getMenuName())
                .transactionId(transactionDetail.getTransaction().getId())
                .qty(transactionDetail.getQty())
                .build();
    }

}

