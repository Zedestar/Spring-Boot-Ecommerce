package com.ecommerce.project.service;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address addressToBeCreated = modelMapper.map(addressDTO, Address.class);
        addressRepository.save(addressToBeCreated);
        return addressDTO;
    }
}
