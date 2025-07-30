package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{

//    private List<Category> categories = new ArrayList<>();
//    Long newCategoryId = 222222222L;


    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
//        return categories;
       return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category){
//        category.setCategoryId(newCategoryId);
//        categories.add(category);
//        this.newCategoryId = this.newCategoryId + 1;
        categoryRepository.save(category);
        return category;
    }

    @Override
    public String deleteCategory(Long categoryId) {
//        Category categoryToDelete =
//                categories.stream().filter((item) -> item.getCategoryId().equals(categoryId)).findFirst().orElse(null);
//        if (categoryToDelete != null) {
//            categories.remove(categoryToDelete);
//            return "The selected category was deleted successfully";
//        } else {
//            return "The category was not found";
//        }

        Category categoryToDelete = categoryRepository.findById(categoryId).orElse(null);
        if (categoryToDelete != null) {
            categoryRepository.delete(categoryToDelete);
            return "The selected category was deleted successfully";
        }else {
            return "The category was not found";
        }

//        try{
//            categoryRepository.deleteById(categoryId);
//            return "Category deleted successfully";
//        }catch (Exception e){
//            return "error " + e.getMessage();
//        }

    }

    @Override
    public String updateCategory(Long categoryId, Category category){
//        Category categoryToUpdate =
//                categories.stream().filter((item) -> item.getCategoryId().equals(categoryId)).findFirst().orElse(null);
//        if(categoryToUpdate != null){
//            categoryToUpdate.setCategoryName(category.getCategoryName());
//            return "The selected category was updated successfully";
//        }else{
//            return "The category was not found";
//        }
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
