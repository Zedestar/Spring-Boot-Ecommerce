package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.CartItemDTO;
import com.ecommerce.project.payload.UserDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.security.services.UserDetailsImpl;
import com.ecommerce.project.utils.GetAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    private List<CartItemDTO> changingCartItemToDTO(Cart cart) {
        return cart.getCartItems().stream().map(cartItem ->
                {
                    CartItemDTO theDTOCartITem = new CartItemDTO();
                    theDTOCartITem.setCartItemId(cartItem.getCartItemId());
                    theDTOCartITem.setProductName(cartItem.getProduct().getName());
                    theDTOCartITem.setProductImage(cartItem.getProduct().getImage());
                    theDTOCartITem.setProductPrice(cartItem.getProduct().getPrice());
                    theDTOCartITem.setCartItePrice(cartItem.getCartProductPrice());
                    theDTOCartITem.setQuantity(cartItem.getQuantity());
                    theDTOCartITem.setDiscount(cartItem.getDiscount());
                    return theDTOCartITem;
                }
        ).toList();
    }

    private Double gettingTotalCartPrice(Cart cart){
        return  cart.getCartItems().stream().map(cartItem ->
        {
            Double totalMoney = 0.0;
            totalMoney += cartItem.getCartProductPrice();
            return totalMoney;
        }).mapToDouble(Double::doubleValue).sum();
    }




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
             cartItem.setCartProductPrice(product.getPrice() * cartItem.getQuantity());
             cartItemRepository.save(cartItem);
         }else {
             CartItem cartItem = new CartItem();
             cartItem.setProduct(product);
             cartItem.setCart(userCart);
             cartItem.setQuantity(quantity);
             cartItem.setProductPrice(product.getPrice());
             cartItem.setCartProductPrice(product.getPrice() * quantity);
             cartItem.setDiscount(product.getDiscount());
             cartItemRepository.save(cartItem);
             userCart.getCartItems().add(cartItem);
         }


       CartDTO cartDTO = new CartDTO();
       cartDTO.setCartId(userCart.getCartId());
       cartDTO.setUserDTO(modelMapper.map(user, UserDTO.class));
       cartDTO.setCartItemDTOList(changingCartItemToDTO(userCart));
       cartDTO.setTotalPrice(gettingTotalCartPrice(userCart));

        return cartDTO;
    }


    @Override
    public List<CartDTO> allCartList() {
        List<Cart> cartList = cartRepository.findAll();
        if(cartList.isEmpty()){
            throw new APIException("There is not cart found");
        }
        return cartList.stream().map(
                cart->{
                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setCartId(cart.getCartId());
                    cartDTO.setUserDTO(modelMapper.map(cart.getUser(), UserDTO.class));
                    cartDTO.setCartItemDTOList(changingCartItemToDTO(cart));
                    cartDTO.setTotalPrice(gettingTotalCartPrice(cart));
                    return cartDTO;
                }
        ).toList();
    }

    @Override
    public List<CartDTO> getUserCartList() {
        User loggedUser = GetAuthenticatedUser.loggedInUser();
        List<Cart> cartList = cartRepository.findAllByUser_UserId(loggedUser.getUserId())
                .orElseThrow(()->  new APIException("There is not cart found"));

        return cartList.stream().map(
                cart->{
                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setCartId(cart.getCartId());
                    cartDTO.setUserDTO(modelMapper.map(cart.getUser(), UserDTO.class));
                    cartDTO.setCartItemDTOList(changingCartItemToDTO(cart));
                    cartDTO.setTotalPrice(gettingTotalCartPrice(cart));
                    return cartDTO;
                }
        ).toList();
    }

    @Override
    public CartItemDTO deleteUserCartItemProduct(Long cartItemId) {
        User currentUser = GetAuthenticatedUser.loggedInUser();
        CartItem cartItemToDelete = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new APIException("This product doesn't exist"));
        if(!cartItemToDelete.getCart().getUser().getUserId().equals(currentUser.getUserId())){
            throw new APIException("This product doesn't exist in your cart");
        }
        cartItemRepository.delete(cartItemToDelete);
        Cart theCartToSupportChangingCartItem = new Cart();
        theCartToSupportChangingCartItem.setCartItems((List<CartItem>) cartItemToDelete);
        return (CartItemDTO) changingCartItemToDTO(theCartToSupportChangingCartItem);
    }
}
