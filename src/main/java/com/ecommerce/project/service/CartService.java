package com.ecommerce.project.service;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.CartItemDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductsToCart(Long productId, Integer quantity);
    List<CartDTO> allCartList();
    List<CartDTO> getUserCartList();
    CartItemDTO deleteUserCartItemProduct(Long cartItemId);
}
