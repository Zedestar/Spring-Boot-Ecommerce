package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.Order;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.OrderRepository;
import com.ecommerce.project.utils.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public String createOrder(Long cartId, Long addressId, String phoneNumber) {
        User loggedUser = GetAuthenticatedUser.loggedInUser();
        Cart cartToAddToOrder = cartRepository.findById(cartId)
                .orElseThrow(()->new APIException("The cart DoesNot exist"));
        Address addressToAddToOrder = addressRepository.findByAddressId(addressId)
                .orElseThrow(()->new APIException("The address DoesNot exist"));

        if(!cartToAddToOrder.getUser().getUserId().equals(loggedUser.getUserId())){
            throw new APIException("You can't add another user's cart");
        }

        if (!addressToAddToOrder.getUser().getUserId().equals(loggedUser.getUserId())) {
            throw new APIException("You can't add another user's address");
        }

        Order theOrder = new Order();
        theOrder.setCart(cartToAddToOrder);
        theOrder.setAddress(addressToAddToOrder);
        theOrder.setPhoneNumber(phoneNumber);
        theOrder.setUser(loggedUser);
        theOrder.setAmount(cartToAddToOrder.getTotalPrice());

        orderRepository.save(theOrder);
        return "The order has beed created successfully";
    }
}
