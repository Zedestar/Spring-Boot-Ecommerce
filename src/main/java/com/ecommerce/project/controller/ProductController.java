package com.ecommerce.project.controller;


import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "productCategoryId", required = false) Long productCategoryId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.getAllProducts(productName, productCategoryId));
    }

    @PostMapping(value = "/admin/{categoryId}/add-product",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> addProduct(
            @Valid
            @RequestPart("productDTO") ProductDTO productDTO,
            @RequestPart("file") MultipartFile file,
            @PathVariable Long categoryId
        ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(categoryId, productDTO, file));
    }

    @DeleteMapping("/admin/delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/admin/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(productId, productDTO));
    }

}
