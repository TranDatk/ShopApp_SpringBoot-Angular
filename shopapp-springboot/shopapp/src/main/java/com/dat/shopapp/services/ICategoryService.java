package com.dat.shopapp.services;

import com.dat.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long id, Category category);
    void deleteCategory(long id);
}
