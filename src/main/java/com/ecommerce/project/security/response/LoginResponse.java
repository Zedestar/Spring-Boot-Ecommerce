package com.ecommerce.project.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class LoginResponse {
    private String jwtToken;
    private Long userId;
    private String username;
    private List<String> roles;

    public LoginResponse(String jwtToken, Long userId, String username, List<String> roles) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }
    public LoginResponse( Long userId, String username, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

}
