package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponse getAllProducts(String productName, Long productCategoryId);
    ProductDTO createProduct(Long categoryId, ProductDTO productDTO, MultipartFile file) throws IOException;
    ProductDTO deleteProduct(Long productId);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
}
