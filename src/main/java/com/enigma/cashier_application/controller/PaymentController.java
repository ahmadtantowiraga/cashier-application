package com.enigma.cashier_application.controller;

import com.enigma.cashier_application.dto.response.CommonResponse;
import com.enigma.cashier_application.entity.Product;
import com.enigma.cashier_application.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> update(@PathVariable String id){
        paymentService.update(id, "Paid");
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .data("Ok")
                .message("transaction has been paid")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
}
