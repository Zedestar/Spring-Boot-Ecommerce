package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String phoneNumber;
    private Double amount;
    private Boolean status;
    private UserDTO user;
    private CartDTO cart;
    private AddressDTO addressDTO;
}
