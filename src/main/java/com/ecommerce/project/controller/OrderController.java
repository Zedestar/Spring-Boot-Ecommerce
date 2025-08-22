package com.ecommerce.project.controller;


import com.ecommerce.project.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create/order/cart/{cartId}/address/{addressId}")
    public ResponseEntity<String> createOrder(@Valid @RequestBody String phoneNumber,
                                              @PathVariable Long cartId,
                                              @PathVariable Long addressId) {
        return ResponseEntity.ok().body(orderService.createOrder(cartId, addressId, phoneNumber));
    }
}
