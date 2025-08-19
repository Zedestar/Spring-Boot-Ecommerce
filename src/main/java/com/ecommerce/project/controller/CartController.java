package com.ecommerce.project.controller;


import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity){
//        CartDTO cartDTO = cartService.addProductsToCart(productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addProductsToCart(productId, quantity));
    }

    @GetMapping("/admin/cart/list")
    public ResponseEntity<List<CartDTO>> gettingAllCartList(){
        return ResponseEntity.ok().body(cartService.allCartList());
    }
}
