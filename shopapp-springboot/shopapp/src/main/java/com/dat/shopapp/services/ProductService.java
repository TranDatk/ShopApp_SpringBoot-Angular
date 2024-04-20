package com.dat.shopapp.services;

import com.dat.shopapp.exceptions.DataNotFoundException;
import com.dat.shopapp.exceptions.InvalidParamException;
import com.dat.shopapp.models.Category;
import com.dat.shopapp.models.Product;
import com.dat.shopapp.models.ProductImage;
import com.dat.shopapp.repositories.CategoryRepository;
import com.dat.shopapp.repositories.ProductImageRepository;
import com.dat.shopapp.repositories.ProductRepository;
import com.dat.shopapp.responses.ProductResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(Product product) throws DataNotFoundException {
        long categoryId = product.getCategory().getId();
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(
                        ()->new DataNotFoundException(String.format("Cannot find category with id = %d",categoryId))
                );
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find product with id = " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(product -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .thumbnail(product.getThumbnail())
                    .description(product.getDescription())
                    .categoryId(product.getCategory().getId())
                    .id(product.getId())
                    .build();
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());
            return productResponse;
        });
    }

    @Override
    public Product updateProduct(long id, Product product) {
        try{
            Product existingProduct = getProductById(product.getId());
            if(existingProduct != null){
                long categoryId = product.getCategory().getId();
                Category existingCategory = categoryRepository.findById(categoryId)
                        .orElseThrow(()->new DataNotFoundException(
                                String.format("Cannot find category with id = %d",categoryId)));
                return productRepository.save(existingProduct);
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresentOrElse(
                productRepository::delete,
                () -> { throw new EntityNotFoundException("Product not found with id: " + id); }
        );
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(ProductImage productImage) throws Exception {
        long productId = productImage.getProduct().getId();
        // Không cần kiểm tra product có tồn tại không, vì đối số được lấy từ createdProduct
        int size = productImageRepository.findByProductId(productId).size();
        if(size > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of images must be less or euqal "
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }else{
            return productImageRepository.save(productImage);
        }
    }
}
