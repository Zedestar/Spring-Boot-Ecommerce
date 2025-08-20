package com.ecommerce.project.service;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.CartItemDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService {
    CartDTO addProductsToCart(Long productId, Integer quantity);
    List<CartDTO> allCartList();
    List<CartDTO> getUserCartList();
    @Transactional
    CartItemDTO deleteUserCartItemProduct(Long cartItemId);
}
