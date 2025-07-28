package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();
    Long newCategoryId = 222222222L;

    @Override
    public List<Category> getAllCategories(){
        return categories;
    }

    @Override
    public Category createCategory(Category category){
        category.setCategoryId(newCategoryId);
        categories.add(category);
        this.newCategoryId = this.newCategoryId + 1;
        return category;
    }

    @Override
    public String deleteCategory(Long categoryId){
        Category categoryToDelete =
                categories.stream().filter((item)->item.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if(categoryToDelete != null){
        categories.remove(categoryToDelete);
        return "The selected category was deleted successfully";
        }else{
            return "The category was not found";
        }
    }
}
