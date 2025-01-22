package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.model.dto.request.UserRequest;
import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.UserResponse;
import com.learning.ecommerceapi.model.entity.Users;
import com.learning.ecommerceapi.service.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UsersController {

    private  final UsersService usersService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<Users>>> findAllUsers(@Positive
                                                                @RequestParam(defaultValue = "1" ) Integer pageNo,
                                                                @RequestParam(defaultValue = "10" ) Integer pageSize,
                                                                @RequestParam(defaultValue = "userName" ) String sortBy,
                                                                @RequestParam(defaultValue = "ASC" ) String sortDirection) {
        ApiResponse apiResponse = ApiResponse.<List<Users>>builder()
                .message("Successfully retrieved all users")
                .status(HttpStatus.OK)
                .payload(usersService.getAllUsers(pageNo, pageSize, sortBy, sortDirection))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Users>> findUserById(@PathVariable UUID userId) {
        ApiResponse apiResponse = ApiResponse.<Users>builder()
                .message("Successfully retrieved user id " + userId)
                .status(HttpStatus.OK)
                .payload(usersService.getUserById(userId))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID userId, @RequestBody UserRequest user) {
        ApiResponse apiResponse = ApiResponse.<UserResponse>builder()
                .message("Successfully updated user id " + userId)
                .status(HttpStatus.OK)
                .payload(usersService.updateUser(userId, user))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable UUID userId) {
        usersService.deleteUser(userId);
        ApiResponse apiResponse = ApiResponse.<UserResponse>builder()
                .message("Successfully deleted user id " + userId)
                .status(HttpStatus.OK)
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
