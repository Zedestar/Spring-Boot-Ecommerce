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

    @Override
    public String deleteAddress(Long addressId) {
        User loggedUser =  GetAuthenticatedUser.loggedInUser();
        Address addressToDelete = addressRepository.findByAddressId(addressId)
                .orElseThrow(()-> new APIException("Address with id " + addressId + " does not exist"));
        if(!addressToDelete.getUser().getUserId().equals(loggedUser.getUserId())) {
            throw new APIException("This address is not yours, you cannot delete it");
        }
        addressRepository.delete(addressToDelete);
        return "The address has been deleted successfully";
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        User loggedUser =  GetAuthenticatedUser.loggedInUser();
        Address addressToUpdate = addressRepository.findByAddressId(addressId)
                .orElseThrow(()-> new APIException("The address you tried to update does not exist"));
        if(!addressToUpdate.getUser().getUserId().equals(loggedUser.getUserId())) {
            throw new APIException("This address is not yours, you cannot update it");
        }
        if (addressDTO.getRegion() != null && !addressDTO.getRegion().isBlank()) {
            addressToUpdate.setRegion(addressDTO.getRegion());
        }
        if (addressDTO.getCity() != null && !addressDTO.getCity().isBlank()) {
            addressToUpdate.setCity(addressDTO.getCity());
        }
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().isBlank()) {
            addressToUpdate.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getBuildNumber() != null && !addressDTO.getBuildNumber().isBlank()) {
            addressToUpdate.setBuildNumber(addressDTO.getBuildNumber());
        }
        if (addressDTO.getPostalCode() != null && !addressDTO.getPostalCode().isBlank()) {
            addressToUpdate.setPostalCode(addressDTO.getPostalCode());
        }
        addressRepository.save(addressToUpdate);
        AddressDTO theAddressDTOToReturn = modelMapper.map(addressToUpdate, AddressDTO.class);
        theAddressDTOToReturn.setUser(modelMapper.map(addressToUpdate.getUser(), UserDTO.class));
        return theAddressDTOToReturn;
    }
}
