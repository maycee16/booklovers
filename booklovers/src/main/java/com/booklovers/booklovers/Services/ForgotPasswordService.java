package com.booklovers.booklovers.Services;


import com.booklovers.booklovers.DTO.ResetPasswordRequest;
import com.booklovers.booklovers.Entity.Otp;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.OtpRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class ForgotPasswordService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String generateOtp() {

        SecureRandom random = new SecureRandom();

        return String.format(
                "%06d",
                random.nextInt(1000000)
        );
    }

    public String sendOtp(String email) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String otp = generateOtp();

        otpRepository.deleteByEmail(email);

        Otp resetOtp =
                new Otp();

        resetOtp.setEmail(email);
        resetOtp.setOtp(otp);

        resetOtp.setExpiryTime(
                LocalDateTime.now().plusMinutes(5)
        );

        otpRepository.save(resetOtp);

        emailService.sendOtpEmail(email, otp);

        return "OTP sent successfully";
    }

    public String resetPassword(
            ResetPasswordRequest request
    ) {

        Otp otpRecord =
                otpRepository.findByEmailAndOtp(
                                request.getEmail(),
                                request.getOtp()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Invalid OTP"));

        if (otpRecord.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "OTP has expired"
            );
        }

        Users user = usersRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        usersRepository.save(user);

        otpRepository.deleteByEmail(
                request.getEmail()
        );

        return "Password updated successfully";
    }
}