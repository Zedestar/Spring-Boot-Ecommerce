package com.ecommerce.project.controller;


import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping("/public/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
          return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
//        return categoryService.createCategory(category);
    }


    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String message = categoryService.deleteCategory(categoryId);
        if(message.equals("The category was not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        String updatingStatusMessage = categoryService.updateCategory(categoryId, category);
        if(updatingStatusMessage.equals("The category was not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(category);
        }else{
            return ResponseEntity.ok(category);
        }
    }
}
