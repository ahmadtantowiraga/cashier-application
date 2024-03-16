package com.enigma.cashier_application.dto.response;

import com.enigma.cashier_application.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String customerName;
    private String mobilePhoneNo;
    private Boolean isMember;
    private String userAccountId;
}
