package com.dat.shopapp.services;

import com.dat.shopapp.exceptions.DataNotFoundException;
import com.dat.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(Product product) throws DataNotFoundException;
    Product getProductById(long id);
    Page<Product> getAllProducts(PageRequest pageRequest);
    Product updateProduct(long id, Product product);
    void deleteProduct(long id);
    boolean existsByName(String name);
}
