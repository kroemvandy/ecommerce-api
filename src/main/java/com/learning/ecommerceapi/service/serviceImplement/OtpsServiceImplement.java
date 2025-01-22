package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.model.dto.request.OtpsRequest;
import com.learning.ecommerceapi.model.entity.Otps;
import com.learning.ecommerceapi.repository.OtpsRepository;
import com.learning.ecommerceapi.service.OtpsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OtpsServiceImplement implements OtpsService {

    private final OtpsRepository otpsRepository;

    @Override
    public OtpsRequest generateOtps() {
        OtpsRequest otpsRequest = new OtpsRequest();
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        StringBuilder otps = new StringBuilder(Integer.toString(randomNumber));
        while (otps.length() < 6) {
            otps.insert(0 , "0");
        }
        otpsRequest.setIssuedAt(LocalDateTime.now());
        otpsRequest.setOtpCode(Long.parseLong(otps.toString()));
        otpsRequest.setExpirationDate(otpsRequest.getIssuedAt().plusMinutes(1));
        return otpsRequest;
    }

    @Override
    public void saveOtps(Otps otps) {
        otpsRepository.save(otps);
    }

    @Override
    public Otps getOtpsByUserId(UUID userId) {
        return otpsRepository.findById(userId).orElseThrow(() -> new ResourceNotFountException("User with id " + userId + " not found"));
    }

    @Override
    public Optional<Otps> getOtpsByCode(Long otpsCode) {
        return otpsRepository.findByOtpsCode(otpsCode);
    }

    @Override
    public void updateOtpForUser(UUID userId, Long newOtpCode, LocalDateTime expirationDate) {
        Otps otps = new Otps();
        otps.setOptsCode(newOtpCode);
        otps.setExpirationDate(expirationDate);
        otpsRepository.save(otps);
    }

    @Override
    public void markOtpAsVerified(UUID userId) {
        Otps otps = getOtpsByUserId(userId);
        otps.setVerify(true);
        otpsRepository.save(otps);
    }

    @Override
    public void resetOtp(UUID userId, Long otpCode, LocalDateTime expirationDate) {
        Otps otps = getOtpsByUserId(userId);
        otps.setOptsCode(otpCode);
        otps.setExpirationDate(expirationDate);
        otps.setVerify(false);
        otpsRepository.save(otps);
    }

}
