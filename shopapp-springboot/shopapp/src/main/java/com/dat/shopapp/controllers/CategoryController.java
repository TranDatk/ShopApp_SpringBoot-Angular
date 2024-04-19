package com.dat.shopapp.controllers;

import com.dat.shopapp.dtos.CategoryDTO;
import com.dat.shopapp.models.Category;
import com.dat.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try{
            Category newCategory = Category
                    .builder()
                    .name(categoryDTO.getName())
                    .build();
            categoryService.createCategory(newCategory);
            return ResponseEntity.ok("Category is created successfully");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ){
        try{
            Category newCategory = Category
                    .builder()
                    .id(id)
                    .name(categoryDTO.getName())
                    .build();
            categoryService.updateCategory(id, newCategory);
            return ResponseEntity.ok("Category is updated successfully");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(String.format("Category with id=%d is deleted successfully", id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
