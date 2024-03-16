package com.enigma.cashier_application.service;

import com.enigma.cashier_application.dto.request.ProductRequest;
import com.enigma.cashier_application.dto.request.SearchRequest;
import com.enigma.cashier_application.dto.response.ProductResponse;
import com.enigma.cashier_application.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
  ProductResponse create(ProductRequest request);
  void update(Product request);
  Page<ProductResponse> findAllProduct(SearchRequest request);
  ProductResponse findById(String id);
  void delete(String id);
}
