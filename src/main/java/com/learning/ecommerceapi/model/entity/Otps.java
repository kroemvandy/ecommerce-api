package com.learning.ecommerceapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "otp_tb")
public class Otps {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID optsId;
    @Column(name = "otp_code", nullable = false)
    private Long optsCode;
    @Column(name = "verify", nullable = false)
    private Boolean verify;
    @Column(name = "is_forget_password", nullable = false)
    private Integer isFortetPassword;
    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;
    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users users;
}
