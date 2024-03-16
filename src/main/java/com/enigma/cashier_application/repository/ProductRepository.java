package com.enigma.cashier_application.repository;

import com.enigma.cashier_application.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Transactional
    @Query(value ="UPDATE m_product SET product_name = :name, price= :price, stock=:stock WHERE id = :id", nativeQuery = true)
    void updateProduct(@Param("id") String id, @Param("name") String name, @Param("price") Long price, @Param("stock") Integer stock);

    @Modifying
    @Query(value= "SELECT * FROM m_product", nativeQuery = true)
    List<Product> findAllProduct();

    @Modifying
    @Query(value= "DELETE FROM m_product WHERE id= :id", nativeQuery = true)
    void deleteById(@Param("id") String id);

}
