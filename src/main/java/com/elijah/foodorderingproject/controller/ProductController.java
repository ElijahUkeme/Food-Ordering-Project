package com.elijah.foodorderingproject.controller;

import com.elijah.foodorderingproject.dto.PopularProductList;
import com.elijah.foodorderingproject.dto.ProductDto;
import com.elijah.foodorderingproject.dto.RecommendedProductList;
import com.elijah.foodorderingproject.model.exception.DataNotFoundException;
import com.elijah.foodorderingproject.model.image.ImageModel;
import com.elijah.foodorderingproject.model.product.Product;
import com.elijah.foodorderingproject.response.ApiResponse;
import com.elijah.foodorderingproject.service.image.ImageService;
import com.elijah.foodorderingproject.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductController {

    private ImageService imageService;
    @Autowired
    private ProductService productService;

    public ProductController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/product/add")
    public ResponseEntity<ApiResponse> uploadUserInfo(@RequestParam("file") MultipartFile file, @RequestPart("product") ProductDto productDto) throws Exception {
        ImageModel imageModel = imageService.saveProductImage(file);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(imageModel.getId())
                .toUriString();
        productService.addAProduct(productDto,downloadUrl);

        return  new ResponseEntity<>(new ApiResponse(true,"Product Added Successfully"), HttpStatus.CREATED);
    }
    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("imageId") String imageId) throws Exception {
        ImageModel imageModel = imageService.getImage(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageModel.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"imageModel; filename=\""+imageModel.getFileName()
                        +"\"")
                .body(new ByteArrayResource(imageModel.getData()));

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId")Integer productId, @RequestBody ProductDto productDto) throws DataNotFoundException {
        productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(new ApiResponse(true,"Product Updated Successfully"),HttpStatus.OK);
    }
    @GetMapping("/popularProduct/list")
    public ResponseEntity<PopularProductList> allPopularProducts(){
        return new ResponseEntity<>(productService.getPopularProducts(), HttpStatus.OK);
    }
    @GetMapping("/recommendedProduct/list")
    public ResponseEntity<RecommendedProductList> allRecommendedProducts(){
        return new ResponseEntity<>(productService.getRecommendedProducts(),HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) throws DataNotFoundException {
        return new ResponseEntity<>(productService.findProductById(productId),HttpStatus.OK);
    }
}
