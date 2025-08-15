package com.ecommerce.project.config;


import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeConfig {


    @Bean
    public CommandLineRunner initRole(RoleRepository roleRepository) {
        return args -> {
            for (AppRole roleName : AppRole.values()) {
                roleRepository.findByRoleName(roleName).orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(roleName);
                    roleRepository.save(newRole);
                    return newRole;
                });
            }
        };
    }
}
