package com.enigma.cashier_application.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "m_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="customer_name", columnDefinition = "VARCHAR(50)")
    private String customerName;

    @Column(name="mobile_phone_no", columnDefinition = "VARCHAR(20)")
    private String mobilePhoneNo;

    @Column(name = "member")
    private Boolean isMember;

    @JoinColumn(name = "user_account_id", unique = true)
    @OneToOne
    private UserAccount userAccount;
}