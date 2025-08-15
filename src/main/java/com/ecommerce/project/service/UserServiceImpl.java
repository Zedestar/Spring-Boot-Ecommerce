package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String SigningUp(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        String email = signupRequest.getEmail();
        String userRole = signupRequest.getUserRole();

        if(userRepository.existsByUsername(username)){
            throw new APIException("That name is already taken. Please choose another one.");
        }

        if(userRepository.existsByEmail(email)){
            throw new APIException("That email is already taken. Please choose another one.");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        switch(userRole){
            case "admin":
                Role adminRole = roleRepository.findByRoleName(AppRole.valueOf("ROLE_ADMIN"))
                        .orElseThrow(()-> new APIException("the role admin is not found"));
               newUser.setRoles(Set.of(adminRole));
               break;
            case "seller":
                Role sellerRole = roleRepository.findByRoleName(AppRole.valueOf("ROLE_SELLER"))
                        .orElseThrow(()-> new APIException("the role admin is not found"));
                newUser.setRoles(Set.of(sellerRole));
                break;
            default:
                Role theUserRole = roleRepository.findByRoleName(AppRole.valueOf("ROLE_USER"))
                        .orElseThrow(()-> new APIException("the role admin is not found"));
                newUser.setRoles(Set.of(theUserRole));
                break;

        }
        userRepository.save(newUser);
        return "The account is successfully created";
    }
}
