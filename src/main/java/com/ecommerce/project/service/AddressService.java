package com.ecommerce.project.service;

import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);
    List<AddressDTO> listingAllAddresses();
    AddressDTO gettingAddressById(Long addressId);
    List<AddressDTO> gettingAddressByLoggedInUser();
    String deleteAddress(Long addressId);
}
