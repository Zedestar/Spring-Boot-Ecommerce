package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ItemAlreadyExistException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        Category newCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if(newCategory != null)
            throw new ItemAlreadyExistException("The category " + category.getCategoryName() + " already exists");
        categoryRepository.save(category);
        return category;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category categoryToDelete =
                categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(categoryToDelete);
        return "Selected category was deleted successfully";
    }

    @Override
    public String updateCategory(Long categoryId, Category category) {
        Category categoryToUpdate =
                categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryToUpdate.setCategoryName(category.getCategoryName());
        categoryRepository.save(categoryToUpdate);
        return "The selected category was updated successfully";

    }

}
