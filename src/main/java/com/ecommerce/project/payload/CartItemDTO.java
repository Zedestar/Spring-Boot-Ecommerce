package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private String productName;
    private String productImage;
//    private CartDTO cartDTO;
    private Integer quantity;
    private Double productPrice;
    private Double discount;
}
