package com.learning.ecommerceapi.mapper;

import com.learning.ecommerceapi.model.dto.request.UserRequest;
import com.learning.ecommerceapi.model.dto.response.UserResponse;
import com.learning.ecommerceapi.model.entity.Users;

import java.time.LocalDateTime;

public class UserMapper {
    public static UserResponse toUserResponse(Users request) {
        UserResponse users = new UserResponse();
        users.setUserName(request.getUserName());
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setEmail(request.getEmail());
        users.setRole(request.getRoleName());
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        return users;
    }
}
