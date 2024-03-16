package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchProductRequest;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
  ProductResponse create(ProductRequest request);
  void update(Product request);
  Page<ProductResponse> findAllProduct(SearchProductRequest request);
  ProductResponse findById(String id);
}
