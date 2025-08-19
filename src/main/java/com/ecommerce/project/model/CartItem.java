package com.ecommerce.project.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cartItems")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId")
//    @JsonManagedReference
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;


    private Integer quantity;
    private Double productPrice;
    private Double discount;
}
