package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
       return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category){
        categoryRepository.save(category);
        return category;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category categoryToDelete = categoryRepository.findById(categoryId).orElse(null);
        if (categoryToDelete != null) {
            categoryRepository.delete(categoryToDelete);
            return "The selected category was deleted successfully";
        }else {
            return "The category was not found";
        }
    }

    @Override
    public String updateCategory(Long categoryId, Category category){
        Category categoryToUpdate = categoryRepository.findById(categoryId).orElse(null);
        if (categoryToUpdate != null){
            categoryToUpdate.setCategoryName(category.getCategoryName());
            categoryRepository.save(categoryToUpdate);
            return "The selected category was updated successfully";
        }else{
            return "The category was not found";
        }
    }

}
