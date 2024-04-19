package com.dat.shopapp.services;

import com.dat.shopapp.models.Category;
import com.dat.shopapp.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long id, Category category) {
        try {
            Category existingCategory = getCategoryById(id);
            existingCategory.setName(category.getName());
            categoryRepository.save(existingCategory);
            return existingCategory;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update category: " + e.getMessage());
        }
    }

    @Override
    public void deleteCategory(long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
        } else {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
    }
}
