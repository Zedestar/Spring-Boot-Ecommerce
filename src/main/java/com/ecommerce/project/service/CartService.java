package com.ecommerce.project.service;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductsToCart(Long productId, Integer quantity);
    List<CartDTO> allCartList();
    List<CartDTO> getUserCartList();
}
