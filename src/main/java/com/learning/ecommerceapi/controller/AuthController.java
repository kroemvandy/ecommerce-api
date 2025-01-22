package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.model.dto.request.LoginRequest;
import com.learning.ecommerceapi.model.dto.request.UserRequest;
import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.AuthResponse;
import com.learning.ecommerceapi.model.dto.response.UserResponse;
import com.learning.ecommerceapi.security.JwtService;
import com.learning.ecommerceapi.service.OtpsService;
import com.learning.ecommerceapi.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpsService otpsService;

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = usersService.loadUserByUsername(username);
            if (userApp == null){
                throw new ResourceNotFountException("Wrong email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())){
                throw new ResourceNotFountException("Wrong password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);} catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        final UserDetails userDetails = usersService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        ApiResponse apiResponse = ApiResponse.<AuthResponse>builder()
                .message("Successfully logged in")
                .status(HttpStatus.OK)
                .payload(authResponse)
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserRequest register) {
        ApiResponse apiResponse = ApiResponse.<UserResponse>builder()
                .message("Successfully logged in")
                .status(HttpStatus.OK)
                .payload(usersService.createUser(register))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/verify")
    @Operation(summary = "Verify account before login")
    public ResponseEntity<ApiResponse<?>> verify(@RequestParam Long otps) {
        otpsService.getOtpsByCode(otps).get().getUsers();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("OTP verified successfully")
                .status(HttpStatus.OK)
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
