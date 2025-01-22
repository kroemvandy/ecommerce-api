package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.UserRequest;
import com.learning.ecommerceapi.model.dto.response.UserResponse;
import com.learning.ecommerceapi.model.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UsersService extends UserDetailsService {
    Users getUserById(UUID userId);
    List<Users> getUserName(String userName);
    UserResponse createUser(UserRequest userRequest);
    List<Users> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    UserResponse updateUser(UUID userId, UserRequest userRequest);
    void deleteUser(UUID userId);
}
