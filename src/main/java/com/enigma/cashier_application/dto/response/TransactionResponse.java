package com.enigma.cashier_application.dto.response;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Date date;
    private String customerId;
    private List<TransactionDetailResponse> transactionDetail;
    private Long totalPrice;
    private String transactionStatus;
}
