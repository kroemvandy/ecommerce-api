package com.learning.ecommerceapi.config;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.model.entity.Users;
import com.learning.ecommerceapi.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@AllArgsConstructor
public class GetCurrentUserConfig {

    private final UsersRepository usersRepository;

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return usersRepository.findUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFountException("User not found with username: " + userDetails.getUsername()));
        }
        throw new RuntimeException("No authentication found");
    }

}
