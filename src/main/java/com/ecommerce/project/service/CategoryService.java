package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    Category createCategory(Category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Long categoryId, Category category);
}
