package com.enigma.cashier_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String menuName;
    private Long price;
    private Integer stock;
}
