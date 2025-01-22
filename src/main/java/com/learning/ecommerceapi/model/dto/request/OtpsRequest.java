package com.learning.ecommerceapi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpsRequest {
    private Long otpCode;
    private LocalDateTime issuedAt;
    private LocalDateTime expirationDate;
    private Boolean verify;
    private Integer isForgetPassword;
    private UUID userId;
}
