package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.Otps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpsRepository extends JpaRepository<Otps, UUID> {
    @Query(value = "SELECT * FROM otp_tb WHERE otps_code = :otps_code", nativeQuery = true)
    Optional<Otps> findByOtpsCode(Long otpCode);
}
