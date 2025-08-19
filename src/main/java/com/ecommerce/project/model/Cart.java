package com.ecommerce.project.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "userId")
//    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.MERGE}, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    private Double totalPrice = 0.0;

}
