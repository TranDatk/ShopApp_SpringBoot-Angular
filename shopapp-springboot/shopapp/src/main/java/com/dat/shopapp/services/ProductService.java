package com.dat.shopapp.services;

import com.dat.shopapp.exceptions.DataNotFoundException;
import com.dat.shopapp.models.Category;
import com.dat.shopapp.models.Product;
import com.dat.shopapp.repositories.CategoryRepository;
import com.dat.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product createProduct(Product product) throws DataNotFoundException {
        long categoryId = product.getCategory().getId();
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(
                        ()->new DataNotFoundException(String.format("Cannot find category with id = %d",categoryId))
                );
        Product newProduct = Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .category(existingCategory)
                .build();
        return newProduct;
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Product updateProduct(long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
