package com.ecommerce.project.service;

import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.services.UserDetailsImpl;

public interface UserService {
    String SigningUp(SignupRequest signupRequest);
    UserDetailsImpl gettingUserInformation();
}
