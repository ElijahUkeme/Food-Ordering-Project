package com.elijah.foodorderingproject.repository.product;

import com.elijah.foodorderingproject.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("select p from Product p where p.typeId:=2")
    List<Product> getAllPopularProduct();

    @Query("select p from Product p where p.typeId:=3")
    List<Product> getAllRecommendedProduct();
}
