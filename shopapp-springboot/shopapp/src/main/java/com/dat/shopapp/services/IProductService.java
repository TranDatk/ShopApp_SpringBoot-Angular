package com.dat.shopapp.services;

import com.dat.shopapp.exceptions.DataNotFoundException;
import com.dat.shopapp.exceptions.InvalidParamException;
import com.dat.shopapp.models.Product;
import com.dat.shopapp.models.ProductImage;
import com.dat.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(Product product) throws Exception;
    Product getProductById(long id) throws Exception;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    Product updateProduct(long id, Product product);
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(ProductImage productImage) throws Exception;
}
