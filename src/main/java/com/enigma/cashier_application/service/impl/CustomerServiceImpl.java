package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.dto.request.CustomerRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.CustomerResponse;
import com.enigma.cashier_application.entity.Customer;
import com.enigma.cashier_application.repository.CustomerRepository;
import com.enigma.cashier_application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Page<CustomerResponse> findAll(SearchRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        List<Customer> customerList = customerRepository.findAllCustomer();

        List<CustomerResponse> responseList = customerList.stream().map(customer -> {
            String userAccountId=customer.getUserAccount()==null ? null : customer.getUserAccount().getId();
            return CustomerResponse.builder()
                    .customerName(customer.getCustomerName())
                    .isMember(customer.getIsMember())
                    .mobilePhoneNo(customer.getMobilePhoneNo())
                    .userAccountId(userAccountId)
                    .build();
        }).toList();
        return new PageImpl<>(responseList, pageable, responseList.size());
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        Customer customer = Customer.builder()
                .customerName(request.getCustomerName())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .build();
        Customer customerResponse = customerRepository.saveAndFlush(customer);
        String userAccount=customerResponse.getUserAccount()==null ? null:customerResponse.getUserAccount().getId();
        return CustomerResponse.builder()
                .customerName(customerResponse.getCustomerName())
                .mobilePhoneNo(customerResponse.getMobilePhoneNo())
                .isMember(customerResponse.getIsMember())
                .userAccountId(userAccount)
                .build();
    }

    @Override
    public List<CustomerResponse> findByName(String name) {
        List<Customer> customerList = customerRepository.findAllByName(name);
        return customerList.stream().map(customer -> {
            String userId=customer.getUserAccount()==null ? null: customer.getUserAccount().getId();
            return CustomerResponse.builder()
                    .customerName(customer.getCustomerName())
                    .mobilePhoneNo(customer.getMobilePhoneNo())
                    .isMember(customer.getIsMember())
                    .userAccountId(userId)
                    .build();
        }).toList();
    }

    @Override
    public void deleteById(String id) {
        findById(id);
        customerRepository.deleteById(id);

    }

    @Override
    public void update(Customer request) {
        findById(request.getId());
        customerRepository.updateCustomer(request.getId(), request.getCustomerName(), request.getMobilePhoneNo());
    }

    @Override
    public CustomerResponse findById(String id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer not found"));
        String userId=customer.getUserAccount()==null ? null: customer.getUserAccount().getId();
        return CustomerResponse.builder()
                .customerName(customer.getCustomerName())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .isMember(customer.getIsMember())
                .userAccountId(userId)
                .build();
    }

    @Override
    public void createCustomerAccount(String id, String name, Boolean member, String phone, String account) {
        customerRepository.create(id, name, member, phone, account);
    }
}
