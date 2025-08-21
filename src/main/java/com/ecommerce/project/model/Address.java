package com.ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank(message = "Enter the region")
    @Size(min = 5, message = "The region must have not less than 5 characters")
    private String region;


    @NotBlank(message = "Enter the city")
    @Size(min = 5, message = "The city must have not less than 5 characters")
    private String city;


    @NotBlank(message = "Enter the street")
    @Size(min = 5, message = "The street must have not less than 5 characters")
    private String street;


    @NotBlank(message = "Enter the build number")
    @Size(min = 3, message = "The build number must have not less than 3 characters")
    private String buildNumber;


    @NotBlank(message = "Enter the postal code")
    @Size(min = 5, message = "The postal code must have not less than 5 characters")
    private String postalCode;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId")
    private User user;

}
