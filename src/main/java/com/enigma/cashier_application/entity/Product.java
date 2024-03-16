package com.enigma.cashier_application.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "m_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="product_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String menuName;

    @Column(name="price", nullable = false, columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;

    @Column(name="stock", nullable = false, columnDefinition = "BIGINT CHECK (stock >= 0)")
    private Integer stock;
}
