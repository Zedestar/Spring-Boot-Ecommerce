package com.ecommerce.project.service;



public interface OrderService {
    String createOrder(Long cartId, Long addressId, String phoneNumber);
}
