package com.enigma.cashier_application.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String id;
    private String transactionId;
    private String productName;
    private Integer qty;
    private Long price;
}
