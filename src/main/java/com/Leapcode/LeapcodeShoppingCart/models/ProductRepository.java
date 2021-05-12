package com.Leapcode.LeapcodeShoppingCart.models;

import com.Leapcode.LeapcodeShoppingCart.models.data.Page;
import com.Leapcode.LeapcodeShoppingCart.models.data.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByCasing(String casing);

    Product findByCasingAndIdNot(String casing, int id);

    List<Product> findAllByCategoryId(int categoryId, Pageable pageable);
}

