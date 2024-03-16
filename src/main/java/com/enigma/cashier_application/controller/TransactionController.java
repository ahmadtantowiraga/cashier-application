package com.enigma.cashier_application.controller;

import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.request.TransactionRequest;
import com.enigma.cashier_application.dto.response.CommonResponse;
import com.enigma.cashier_application.dto.response.PagingResponse;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.dto.response.TransactionResponse;
import com.enigma.cashier_application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionResponse>> create(@RequestBody TransactionRequest request){
        TransactionResponse response=transactionService.create(request);
        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .data(response)
                .message("Successfully create transaction")
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    };
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionResponse>> findById(@PathVariable(name = "id") String id){
        TransactionResponse response=transactionService.findById(id);
        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .data(response)
                .message("Successfully update data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> findAll(
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
        Page<TransactionResponse> listProduct = transactionService.findAll(searchRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(listProduct.getTotalPages() + 1)
                .size(listProduct.getSize())
                .hasPrevious(listProduct.hasPrevious())
                .hasNext(listProduct.hasNext())
                .totalElement(listProduct.getTotalElements())
                .totalPages(listProduct.getTotalPages())
                .build();
        CommonResponse<List<TransactionResponse>> commonResponse = CommonResponse.<List<TransactionResponse>>builder()
                .data(listProduct.getContent())
                .message("Successfully get data")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
}
