package com.ecommerce.project.service;

import com.ecommerce.project.payload.UserDTO;
import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.services.UserDetailsImpl;

public interface UserService {
    String SigningUp(SignupRequest signupRequest);
    UserDTO gettingUserInformation();
}
