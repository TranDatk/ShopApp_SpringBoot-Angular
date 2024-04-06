package com.dat.shopapp.controllers;

import com.dat.shopapp.dtos.CategoryDTO;
import com.dat.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(
            @PathVariable("user_id") Long userId
    ) {
        try{
            return ResponseEntity.ok("get order by user" + userId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrders(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Order created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrders(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        try{
            return ResponseEntity.ok("Update");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrders(
            @PathVariable("id") Long id
    ) {
        try{
            return ResponseEntity.ok("Delete");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
