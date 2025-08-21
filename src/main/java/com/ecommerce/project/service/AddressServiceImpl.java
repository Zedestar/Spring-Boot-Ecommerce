package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.UserDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.utils.GetAuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        User userCart = userRepository.findByUserId((addressDTO.getUser().getUserId()));
        if(userCart==null){
            throw new APIException("The user doesn't exist");
        }
        Address addressToBeCreated = modelMapper.map(addressDTO, Address.class);
        addressToBeCreated.setUser(userCart);
        addressRepository.save(addressToBeCreated);
        addressDTO.setUser(modelMapper.map(userCart,  UserDTO.class));
        return addressDTO;
    }

    @Override
    public List<AddressDTO> listingAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        if(addressList.isEmpty()){
            throw new APIException("No address found");
        }

        return addressList.stream()
                .map(address -> {
                    AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
                    addressDTO.setUser(modelMapper.map(address.getUser(), UserDTO.class));
                    return addressDTO;
                }).toList();
    }

    @Override
    public AddressDTO gettingAddressById(Long addressId) {
        Address address = addressRepository.findByAddressId(addressId)
                .orElseThrow(()-> new APIException("The address with id " + addressId + " does not exist"));
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        addressDTO.setUser(modelMapper.map(address.getUser(), UserDTO.class));
        return addressDTO;
    }

    @Override
    public List<AddressDTO> gettingAddressByLoggedInUser() {
        User user = GetAuthenticatedUser.loggedInUser();
        List<Address> addressList =  addressRepository.findAllByUser_UserId((user.getUserId()));
        if(addressList.isEmpty()){
            throw new APIException("You haven't created any address");
        }

        return addressList.stream()
                .map(address -> {
                    AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
                    addressDTO.setUser(modelMapper.map(address.getUser(), UserDTO.class));
                    return addressDTO;
                }).toList();
    }
}
