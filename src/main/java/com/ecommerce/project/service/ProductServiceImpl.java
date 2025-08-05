package com.ecommerce.project.service;


import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public ProductResponse getAllProducts(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new APIException("No product found");
        }
        List<ProductDTO> productListDTO =
                products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productListDTO);
        return productResponse;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productToAdd =productRepository.save(modelMapper.map(productDTO, Product.class));
        return modelMapper.map(productToAdd, ProductDTO.class);
    }
}
