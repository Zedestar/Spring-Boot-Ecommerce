package com.ecommerce.project.controller;


import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressService addressService;


    @PostMapping("/public/users/add/address")
    ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(addressDTO));
    }

    @GetMapping("/admin/get/all/addresses")
    ResponseEntity<List<AddressDTO>> getAllAddresses(){
        return ResponseEntity.ok().body(addressService.listingAllAddresses());
    }

    @GetMapping("/admin/get/address/id/{addressId}")
    ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        return ResponseEntity.ok().body(addressService.gettingAddressById(addressId));
    }
}
