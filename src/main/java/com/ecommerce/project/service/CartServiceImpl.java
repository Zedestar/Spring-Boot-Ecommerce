package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.CartItemDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.security.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService{


    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ModelMapper modelMapper;




    @Override
    public CartDTO addProductsToCart(Long productId, Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();


        Product product = productRepository.findByProductId(productId)
                .orElseThrow(()->new APIException("This product doesn't exist"));

        Cart userCart = cartRepository.findByUser_UserId(userDetails.getUserId())
                .orElseGet(()->{
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        cartRepository.save(newCart);
                        return newCart;
                        }
                );

         Optional<CartItem> existingCart = userCart.getCartItems().stream()
               .filter(cartItem -> cartItem
                       .getProduct()
                       .getProductId()
                       .equals(productId)).findFirst();
         if (existingCart.isPresent()){

             CartItem cartItem = existingCart.get();

             cartItem.setQuantity(cartItem.getQuantity() + quantity);
             cartItem.setProductPrice(product.getPrice() * cartItem.getQuantity());
             cartItemRepository.save(cartItem);
         }else {
             CartItem cartItem = new CartItem();
             cartItem.setProduct(product);
             cartItem.setCart(userCart);
             cartItem.setQuantity(quantity);
             cartItem.setProductPrice(product.getPrice() * quantity);
             cartItem.setDiscount(product.getDiscount());
             cartItemRepository.save(cartItem);
             userCart.getCartItems().add(cartItem);
         }
         List<CartItemDTO> cartItemsDTOs = userCart.getCartItems().stream().map(cartItem ->
                 {
                     CartItemDTO theDTOCartITem = new CartItemDTO();
                     theDTOCartITem.setCartItemId(cartItem.getCartItemId());
                     theDTOCartITem.setProductName(cartItem.getProduct().getName());
                     theDTOCartITem.setProductImage(cartItem.getProduct().getImage());
                     theDTOCartITem.setProductPrice(cartItem.getProductPrice());
                     theDTOCartITem.setQuantity(cartItem.getQuantity());
                     theDTOCartITem.setDiscount(cartItem.getDiscount());
                     return theDTOCartITem;
                 }
                 ).toList();

         Double cartTotalPrice = userCart.getCartItems().stream().map(cartItem ->
             {
                 Double totalMoney = 0.0;
                 totalMoney += cartItem.getProductPrice();
                 return totalMoney;
             }).mapToDouble(Double::doubleValue).sum();

               CartDTO cartDTO = new CartDTO();
               cartDTO.setCartItemDTOList(cartItemsDTOs);
               cartDTO.setTotalPrice(cartTotalPrice);

        return cartDTO;
    }
}
