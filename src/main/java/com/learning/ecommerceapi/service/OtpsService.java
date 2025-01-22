package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.OtpsRequest;
import com.learning.ecommerceapi.model.entity.Otps;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface OtpsService {
    /**
     * Generates a new OTP with its issued and expiration times.
     * @return OtpsRequest containing OTP details.
     */
    OtpsRequest generateOtps();

    /**
     * Saves a new OTP entity.
     * @param otps the OTP entity to save.
     */
    void saveOtps(Otps otps);

    /**
     * Retrieves OTP details by the user ID.
     * @param userId the UUID of the user.
     * @return Otps entity for the given user.
     */
    Otps getOtpsByUserId(UUID userId);

    /**
     * Retrieves OTP details by the OTP code.
     *
     * @param otpCode the OTP code.
     * @return Optional containing the OTP entity if found.
     */
    Optional<Otps> getOtpsByCode(Long otpCode);

    /**
     * Updates the OTP code and expiration date for a specific user.
     * @param userId the UUID of the user.
     * @param newOtpCode the new OTP code.
     * @param expirationDate the new expiration date.
     */
    void updateOtpForUser(UUID userId, Long newOtpCode, LocalDateTime expirationDate);

    /**
     * Marks the OTP as verified for a specific user.
     * @param userId the UUID of the user.
     */
    void markOtpAsVerified(UUID userId);

    /**
     * Resets the OTP details for a specific user.
     * @param userId the UUID of the user.
     * @param otpCode the new OTP code.
     * @param expirationDate the new expiration date.
     */
    void resetOtp(UUID userId, Long otpCode, LocalDateTime expirationDate);
}
