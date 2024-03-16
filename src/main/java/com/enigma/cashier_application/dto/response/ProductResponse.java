package com.enigma.cashier_application.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String menuName;
    private Long price;
    private Integer stock;
}
