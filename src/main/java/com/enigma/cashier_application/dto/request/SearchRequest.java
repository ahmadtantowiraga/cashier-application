package com.enigma.cashier_application.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    private Integer size;
    private Integer page;
    private String sortBy;
    private String direction;
}
