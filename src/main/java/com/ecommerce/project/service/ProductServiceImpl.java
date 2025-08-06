package com.ecommerce.project.service;


import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;


    private String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueUUID = UUID.randomUUID().toString();
        String fileName = uniqueUUID.concat(ext);

        String filePath = path + File.separator + fileName;
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }


    @Override
    public ProductResponse getAllProducts(String productName, Long productCategoryId) {
        List<Product> products;
        if (productCategoryId != null) {
            Category category = categoryRepository.findById(productCategoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "with", productCategoryId));
            products = productRepository.findByCategory(category);
        } else if (productName != null) {
            products = productRepository.findByNameLikeIgnoreCase( '%' + productName + '%');
        } else {
            products = productRepository.findAll();
        }
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
    public ProductDTO createProduct(Long categoryId, ProductDTO productDTO, MultipartFile file) throws IOException {
        Category productCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id ", categoryId ));
        Product productToAdd =modelMapper.map(productDTO, Product.class);
        String path = "images/";
        String imagepath = uploadImage(path, file);
        productToAdd.setImage(imagepath);
        productToAdd.setCategory(productCategory);
        productRepository.save(productToAdd);
        return modelMapper.map(productToAdd, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId){
        Product productToDelete = productRepository.findById(productId).orElseThrow( () -> new ResourceNotFoundException("Product", "with", productId));
        productRepository.delete(productToDelete);
        return modelMapper.map(productToDelete, ProductDTO.class);
    }


//    THis one is not working effectively, it adds instead of updating
    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO){
      Product productToUpdate = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "with", productId));
      Category isCategoryPresent =
              categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "with",  productDTO.getCategoryId()));
        productToUpdate.setName(productDTO.getName());
        productToUpdate.setDescription(productDTO.getDescription());
        productToUpdate.setImage(productDTO.getImage());
        productToUpdate.setPrice(productDTO.getPrice());
        productToUpdate.setDiscount(productDTO.getDiscount());
        productToUpdate.setSpecialPrice(productDTO.getSpecialPrice());
        productToUpdate.setQuantity(productDTO.getQuantity());
        productToUpdate.setCategory(isCategoryPresent);
      productRepository.save(productToUpdate);
      return modelMapper.map(productToUpdate, ProductDTO.class);
    }
}


