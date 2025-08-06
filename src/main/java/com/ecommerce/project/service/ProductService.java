package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductResponse getAllProducts(String productName, Long productCategoryId);
    ProductDTO createProduct(Long categoryId, ProductDTO productDTO);
    ProductDTO deleteProduct(Long productId);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
}
