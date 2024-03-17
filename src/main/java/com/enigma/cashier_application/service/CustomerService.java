package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.request.CustomerRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.CustomerResponse;
import com.enigma.cashier_application.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {
    Page<CustomerResponse> findAll(SearchRequest request);
    CustomerResponse create(CustomerRequest request);
    List<CustomerResponse> findByName(String name);
    void deleteById(String id);
    void update(Customer request);
    CustomerResponse findById(String id);
    void createCustomerAccount(String id, String name, Boolean member, String phone, String account);
}
