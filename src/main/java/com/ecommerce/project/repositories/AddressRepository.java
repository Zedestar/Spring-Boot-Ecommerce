package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByAddressId(Long addressId);

    List<Address> findAllByUser_UserId(Long userUserId);
}
