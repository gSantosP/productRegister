package com.productRegister.repository;

import java.util.List;
import com.productRegister.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByPublished(boolean published);

  List<Product> findByTitleContainingIgnoreCase(String title);
}