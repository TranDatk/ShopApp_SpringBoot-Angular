package com.dat.shopapp.controllers;

import com.dat.shopapp.dtos.OrderDTO;
import com.dat.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @PathVariable("id") Long id
    ) {
        try{
            return ResponseEntity.ok("get order detail" + id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
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
            return ResponseEntity.ok("Order detail created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetails(
            @PathVariable("order_id") Long orderId
    ) {
        try{
            return ResponseEntity.ok("get order details " + orderId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        try{
            return ResponseEntity.ok("Update order detail");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @PathVariable("id") Long id
    ) {
        try{
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
