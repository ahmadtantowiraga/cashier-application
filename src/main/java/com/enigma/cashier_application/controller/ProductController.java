package com.enigma.cashier_application.controller;

import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.CommonResponse;
import com.enigma.cashier_application.dto.response.PagingResponse;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.entity.Product;
import com.enigma.cashier_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/products")
public class ProductController {
    private final ProductService productService;
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<ProductResponse>> create(@RequestBody ProductRequest request){
        ProductResponse response=productService.create(request);
        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .data(response)
                .message("Successfully create data")
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    };
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> update(@RequestBody Product request){
        productService.update(request);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .data("Ok")
                .message("Successfully update data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ProductResponse>>> findAll(
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
        Page<ProductResponse> listProduct = productService.findAllProduct(searchRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(listProduct.getTotalPages() + 1)
                .size(listProduct.getSize())
                .hasPrevious(listProduct.hasPrevious())
                .hasNext(listProduct.hasNext())
                .totalElement(listProduct.getTotalElements())
                .totalPages(listProduct.getTotalPages())
                .build();
        CommonResponse<List<ProductResponse>> commonResponse = CommonResponse.<List<ProductResponse>>builder()
                .data(listProduct.getContent())
                .message("Successfully get data")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<ProductResponse>> findById(@PathVariable(name = "id") String id){
        ProductResponse response=productService.findById(id);
        CommonResponse<ProductResponse> commonResponse = CommonResponse.<ProductResponse>builder()
                .data(response)
                .message("Successfully update data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id){
        productService.delete(id);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .data("Ok")
                .message("Successfully delete data")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    };


}
