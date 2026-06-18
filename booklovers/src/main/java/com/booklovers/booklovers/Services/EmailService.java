package com.booklovers.booklovers.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String email, String otp) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Book Lovers - Password Reset OTP");

            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>
                <body style="margin:0;padding:0;background-color:#f8f4ef;font-family:Arial,sans-serif;">
                
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="center">

                                <table width="600" cellpadding="0" cellspacing="0"
                                       style="background:#ffffff;
                                              margin:30px auto;
                                              border-radius:10px;
                                              overflow:hidden;
                                              box-shadow:0 4px 12px rgba(0,0,0,0.1);">

                                    <!-- Header -->
                                    <tr>
                                        <td style="background:#6F4E37;padding:25px;text-align:center;">
                                            <h1 style="color:white;margin:0;">
                                                📚 Book Lovers
                                            </h1>
                                        </td>
                                    </tr>

                                    <!-- Content -->
                                    <tr>
                                        <td style="padding:40px;">

                                            <h2 style="color:#6F4E37;margin-top:0;">
                                                Password Reset Request
                                            </h2>

                                            <p style="color:#555;font-size:16px;line-height:1.6;">
                                                We received a request to reset your password.
                                                Use the OTP below to continue:
                                            </p>

                                            <div style="text-align:center;margin:30px 0;">
                                                <span style="
                                                    display:inline-block;
                                                    background:#D2B48C;
                                                    color:#4A2C1A;
                                                    font-size:32px;
                                                    font-weight:bold;
                                                    letter-spacing:8px;
                                                    padding:15px 30px;
                                                    border-radius:8px;">
                                                    {{OTP}}
                                                </span>
                                            </div>

                                            <p style="color:#555;font-size:15px;">
                                                This OTP will expire in
                                                <strong>10 minutes</strong>.
                                            </p>

                                            <p style="color:#555;font-size:15px;">
                                                If you did not request a password reset,
                                                you can safely ignore this email.
                                            </p>

                                        </td>
                                    </tr>

                                    <!-- Footer -->
                                    <tr>
                                        <td style="background:#f3ece4;padding:20px;text-align:center;">
                                            <p style="margin:0;color:#777;font-size:13px;">
                                                © 2026 Book Lovers. All rights reserved.
                                            </p>
                                        </td>
                                    </tr>

                                </table>

                            </td>
                        </tr>
                    </table>

                </body>
                </html>
                """;

            htmlContent = htmlContent.replace("{{OTP}}", otp);

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}