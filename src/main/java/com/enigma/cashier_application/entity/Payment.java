package com.enigma.cashier_application.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="m_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @OneToOne(mappedBy = "payment")
    private Transaction transaction;
}
