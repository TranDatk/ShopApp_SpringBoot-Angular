package com.dat.shopapp.controllers;

import com.dat.shopapp.dtos.CategoryDTO;
import com.dat.shopapp.dtos.ProductDTO;
import com.dat.shopapp.models.Category;
import com.dat.shopapp.models.Product;
import com.dat.shopapp.models.ProductImage;
import com.dat.shopapp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.dat.shopapp.utils.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("getAll ,page=%d, limit=%d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok("get product id=" + id);
    }


    @PostMapping(value = "")
    public ResponseEntity<?> createProducts(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = Product
                    .builder()
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .thumbnail(productDTO.getThumbnail())
                    .description(productDTO.getDescription())
                    .category(Category.builder().id(productDTO.getCategoryId()).build())
                    .build();
            Product createdProduct = productService.createProduct(newProduct);

            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @Valid @ModelAttribute("files") List<MultipartFile> files,
            @PathVariable Long id

    ){
        try{
            files = files == null ? new ArrayList<MultipartFile>() : files;
            Product existingProduct = productService.getProductById(id);
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is to large!");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String imageUrl = utils.storeFile(file);

                ProductImage productImage = ProductImage.builder()
                        .imageUrl(imageUrl)
                        .product(existingProduct)
                        .build();
                ProductImage createdProductImage = productService.createProductImage(productImage);
                productImages.add(createdProductImage);
            }
            return ResponseEntity.ok().body(productImages);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProducts(@PathVariable Long id) {
        return ResponseEntity.ok("update " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("delete " + id);
    }
}
