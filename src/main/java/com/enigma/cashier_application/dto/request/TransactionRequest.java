package com.enigma.cashier_application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    @NotBlank(message = "customerId is required")
    private String customerId;

    @NotNull(message = "transaction detail is required")
    private List<TransactionDetailRequest> transactionDetail;
}
