package com.ecommerce.project.service;

import com.ecommerce.project.security.request.SignupRequest;

public interface UserService {
    String SigningUp(SignupRequest signupRequest);
}
