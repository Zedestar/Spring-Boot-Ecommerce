package com.ecommerce.project.service;


import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService{


    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
}
