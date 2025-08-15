package com.ecommerce.project.security.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Provide username to continue")
    @Size(min = 5, max = 20, message = "Username should range from 5 to 20 characters")
    private String username;

    @NotBlank(message = "Provide email to continue")
    @Email
    private String email;

    @NotBlank(message = "Provide password to continue")
    @Size(min = 6, message = "Password should contain at least 6 characters")
    private String password;
}
