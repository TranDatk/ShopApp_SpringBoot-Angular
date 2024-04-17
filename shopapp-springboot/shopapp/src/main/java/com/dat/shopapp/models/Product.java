package com.dat.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product extends BaseEnitity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 350)
    private String name;

    private Float price;

    @Column(length = 300)
    private String thumbnail;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
