//package com.ecommerce.project.service;
//
//import com.ecommerce.project.exceptions.APIException;
//import com.ecommerce.project.model.AppRole;
//import com.ecommerce.project.model.Role;
//import com.ecommerce.project.model.User;
//import com.ecommerce.project.repositories.UserRepository;
//import com.ecommerce.project.security.request.SignupRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//@Service
//public class UserServiceImpl implements UserService{
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public String SigningUp(SignupRequest signupRequest) {
//        String username = signupRequest.getUsername();
//        String password = signupRequest.getPassword();
//        String email = signupRequest.getEmail();
//
//        if(userRepository.existsByUsername(username)){
//            throw new APIException("That name is already taken. Please choose another one.");
//        }
//
//        if(userRepository.existsByEmail(email)){
//            throw new APIException("That email is already taken. Please choose another one.");
//        }
//
//        Role userRole = new Role();
//        Role adminRole = new Role();
//        Role sellerRole = new Role();
//        userRole.setRoleName(AppRole.ROLE_USER);
//        adminRole.setRoleName(AppRole.ROLE_ADMIN);
//        sellerRole.setRoleName(AppRole.ROLE_SELLER);
//
//
//        User newUser = new User();
//        newUser.setUsername(username);
//        newUser.setEmail(email);
//        newUser.setRoles(Set.of(sellerRole, adminRole));
//        newUser.setPassword(passwordEncoder.encode(password));
//
//
//        return "The account is successfully created";
//    }
//}
