package com.enigma.cashier_application.controller;

import com.enigma.cashier_application.dto.request.CustomerRequest;
import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.CommonResponse;
import com.enigma.cashier_application.dto.response.CustomerResponse;
import com.enigma.cashier_application.dto.response.PagingResponse;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.entity.Customer;
import com.enigma.cashier_application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy
    ){
        SearchRequest searchRequest = SearchRequest.builder()
                .direction(direction)
                .sortBy(sortBy)
                .size(size)
                .page(page)
                .build();
        Page<CustomerResponse> listCustomer = customerService.findAll(searchRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(listCustomer.getTotalPages() + 1)
                .size(listCustomer.getSize())
                .hasPrevious(listCustomer.hasPrevious())
                .hasNext(listCustomer.hasNext())
                .totalElement(listCustomer.getTotalElements())
                .totalPages(listCustomer.getTotalPages())
                .build();
        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .data(listCustomer.getContent())
                .message("Successfully get data")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CustomerResponse>> create(@RequestBody CustomerRequest request){
        CustomerResponse response=customerService.create(request);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .data(response)
                .message("Successfully create data")
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    };
    @GetMapping(path = "names/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> findByName(@PathVariable(name = "name") String name){
        List<CustomerResponse> response=customerService.findByName(name);
        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .data(response)
                .message("Successfully search data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CustomerResponse>> findById(@PathVariable(name = "id") String id){
        CustomerResponse response=customerService.findById(id);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .data(response)
                .message("Successfully search data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable(name = "id") String id){
        customerService.deleteById(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message("Successfully delete data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> update(@RequestBody Customer request){
        customerService.update(request);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message("Successfully update data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };

}
