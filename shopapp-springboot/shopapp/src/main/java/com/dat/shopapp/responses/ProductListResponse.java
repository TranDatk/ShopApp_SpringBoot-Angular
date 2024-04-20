package com.dat.shopapp.responses;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Builder
public class ProductListResponse {
    private int totalPages;
    private List<ProductResponse> products;
}
