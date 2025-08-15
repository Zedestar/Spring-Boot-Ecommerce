package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private ProductDTO productDTO;
    private CartDTO cartDTO;
    private Integer productQuantity;
    private Double productPrice;
    private Double discount;
}
