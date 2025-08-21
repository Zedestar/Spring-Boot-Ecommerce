package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    Long addressId;
    String region;
    String city;
    String street;
    String buildNumber;
    String postalCode;
    UserDTO user;
}
