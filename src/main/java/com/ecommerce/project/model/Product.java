package com.ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;


    @NotBlank(message = "Product name should not be empty")
    @Size(min=5, max = 255, message = "Name of the product should be between 5 and 255" )
    private String productName;


    @NotBlank(message = "Product description should not be empty")
    @Size(min=5, max = 255, message = "Description of the product should be between 5 and 255" )
    private String description;


    @PositiveOrZero(message = "The product quantity can't be negative")
    @Column(nullable = false)
    private Integer quantity;


    @PositiveOrZero(message = "The product price can't be negative")
    @Column(nullable = false)
    private Double price;


    @PositiveOrZero(message = "The product discount can't be negative")
    @Column(nullable = false)
    private Double discount;


    @PositiveOrZero(message = "The product special price can't be negative")
    @Column(nullable = false)
    private Double specialPrice;

    @Size(min=5, max = 255, message = "Product image path should be between 5 and 255" )
    private String image;


//    Establishing the relationship with Category Model
    @ManyToOne( cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryId")
    private Category category;


}
