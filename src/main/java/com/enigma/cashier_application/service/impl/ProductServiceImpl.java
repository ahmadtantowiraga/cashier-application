package com.enigma.cashier_application.service.impl;

import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.entity.Product;
import com.enigma.cashier_application.repository.ProductRepository;
import com.enigma.cashier_application.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .price(request.getPrice())
                .menuName(request.getMenuName())
                .stock(request.getStock()).build();
        productRepository.saveAndFlush(product);
        return ProductResponse.builder()
                .price(product.getPrice())
                .menuName(product.getMenuName())
                .stock(product.getStock())
                .build();
    }

    @Override
    @Transactional
    public void update(Product request) {
        findById(request.getId());
        productRepository.updateProduct(request.getId(), request.getMenuName(), request.getPrice(), request.getStock());
    }

    @Override
    @Transactional
    public Page<ProductResponse> findAllProduct(SearchRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        List<Product> products = productRepository.findAllProduct();
        List<ProductResponse> productResponses = products.stream().map(product -> {
            return ProductResponse.builder()
                    .price(product.getPrice())
                    .menuName(product.getMenuName())
                    .stock(product.getStock())
                    .build();
        }).toList();
        return new PageImpl<>(productResponses, pageable, productResponses.size());
    }

    @Override
    @Transactional
    public ProductResponse findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
        return ProductResponse.builder()
                .stock(product.getStock())
                .price(product.getPrice())
                .menuName(product.getMenuName())
                .build();
    }

    @Override
    @Transactional
    public void delete(String id) {
        findById(id);
        productRepository.deleteById(id);
    }

}
