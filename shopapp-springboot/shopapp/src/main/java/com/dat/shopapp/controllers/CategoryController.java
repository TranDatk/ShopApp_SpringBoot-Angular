package com.dat.shopapp.controllers;

import com.dat.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok(String.format("getAll ,page=%d, limit=%d", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> createCategories(
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
        return ResponseEntity.ok("create");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategories(@PathVariable Long id){
        return ResponseEntity.ok("update "+id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id){
        return ResponseEntity.ok("delete "+id);
    }
}
