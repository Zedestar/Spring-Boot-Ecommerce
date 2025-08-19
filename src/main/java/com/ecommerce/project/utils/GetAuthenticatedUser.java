package com.ecommerce.project.utils;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetAuthenticatedUser {
    public static User loggedInUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null
            || !authentication.isAuthenticated()
            || authentication.getPrincipal() == null
            || authentication.getPrincipal().equals("anonymousUser")){
        throw new APIException("Login required to access this resource");
    }
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
