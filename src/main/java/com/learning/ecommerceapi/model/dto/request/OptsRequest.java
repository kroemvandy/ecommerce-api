package com.learning.ecommerceapi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptsRequest {
    private Long otpsCode;
    private LocalDateTime issuedAt;
    private LocalDateTime expirationDate;
    private Boolean verify;
    private Integer isForgot;
    private UUID user;
}
