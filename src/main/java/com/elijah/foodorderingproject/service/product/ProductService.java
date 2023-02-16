package com.elijah.foodorderingproject.service.product;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.elijah.foodorderingproject.dto.PopularProductList;
import com.elijah.foodorderingproject.dto.ProductDto;
import com.elijah.foodorderingproject.dto.RecommendedProductList;
import com.elijah.foodorderingproject.model.exception.DataNotFoundException;
import com.elijah.foodorderingproject.model.product.Product;
import com.elijah.foodorderingproject.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

        public void addAProduct(ProductDto productDto,String productImage){
            Product product = new Product();
            product.setName(productDto.getName());
            product.setTypeId(productDto.getTypeId());
            product.setImage(productImage);
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCreatedDate(new Date());
            product.setLocation(productDto.getLocation());
            product.setStars(productDto.getStars());
            productRepository.save(product);
        }
        public Product getProducts(Product product){
            Product product1 = new Product();
            product1.setStars(product.getStars());
            product1.setId(product.getId());
            product1.setImage(product.getImage());
            product1.setPrice(product.getPrice());
            product1.setDescription(product.getDescription());
            product1.setName(product.getName());
            product1.setLocation(product.getLocation());
            product1.setUpdatedDate(product.getUpdatedDate());
            product1.setCreatedDate(product.getCreatedDate());
            return product1;
        }
        List<Product> getAllPopularProductList(){
            List<Product> popularProductList = productRepository.getAllPopularProduct();
            List<Product> productList = new ArrayList<>();
            for (Product product: popularProductList){
                productList.add(getProducts(product));
            }
            return productList;
        }

    List<Product> getAllRecommendedProductList(){
        List<Product> popularProductList = productRepository.getAllRecommendedProduct();
        List<Product> productList = new ArrayList<>();
        for (Product product: popularProductList){
            productList.add(getProducts(product));
        }
        return productList;
    }
    public Product updateProduct(ProductDto productDto, Integer productId) throws DataNotFoundException {
            Optional<Product> product = productRepository.findById(productId);
            if (!product.isPresent()){
                throw new DataNotFoundException("Product with this id not found");
            }
        if (Objects.nonNull(productDto.getName())&& !"".equalsIgnoreCase(productDto.getName())){
            product.get().setName(productDto.getName());
        }
        if (Objects.nonNull(productDto.getPrice())){
            product.get().setPrice(productDto.getPrice());
        }
        if (Objects.nonNull(productDto.getStars())){
            product.get().setStars(productDto.getStars());
        }
        if (Objects.nonNull(productDto.getLocation())&& !"".equalsIgnoreCase(productDto.getLocation())){
            product.get().setLocation(productDto.getLocation());
        }
        if (Objects.nonNull(productDto.getDescription())&& !"".equalsIgnoreCase(productDto.getDescription())){
            product.get().setDescription(productDto.getDescription());
        }
        if (Objects.nonNull(productDto.getTypeId())){
            product.get().setTypeId(productDto.getTypeId());
        }
        product.get().setUpdatedDate(new Date());
        return productRepository.save(product.get());
    }
    public Product findProductById(Integer productId) throws DataNotFoundException {
            Optional<Product> product = productRepository.findById(productId);
            if (Objects.isNull(product)){
                throw new DataNotFoundException("Product Id not found");
            }
            return product.get();
    }
    public PopularProductList getPopularProducts(){
            PopularProductList productList = new PopularProductList(getAllPopularProductList());
            return productList;
    }
    public RecommendedProductList getRecommendedProducts(){
        return new RecommendedProductList(getAllRecommendedProductList());
    }
}
