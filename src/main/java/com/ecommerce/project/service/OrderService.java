package com.ecommerce.project.service;


import com.ecommerce.project.payload.OrderDTO;

import java.util.List;

public interface OrderService {
    String createOrder(Long cartId, Long addressId, String phoneNumber);
    List<OrderDTO> getAllOrdersForLoggedUser();
}
