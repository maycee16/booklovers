package com.booklovers.booklovers.Utility;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private byte[] secretKeyBytes;

    private static final long EXPIRATION_MILLIS =
            24 * 60 * 60 * 1000; // 24 Hours

    @PostConstruct
    public void init() {

        if (secretKeyString == null || secretKeyString.isBlank()) {
            throw new IllegalStateException("JWT secret is missing");
        }

        secretKeyBytes = secretKeyString.getBytes();

        if (secretKeyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 32 bytes"
            );
        }
    }

    /**
     * Generate JWT Token
     */
    public String generateToken(
            Long userId,
            String email,
            String name
    ) throws JOSEException {

        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(email)
                .issuer("BookLovers")
                .issueTime(Date.from(now))
                .expirationTime(
                        Date.from(now.plusMillis(EXPIRATION_MILLIS))
                )

                // Custom Claims
                .claim("userId", userId)
                .claim("email", email)
                .claim("name", name)

                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.HS256)
                        .type(JOSEObjectType.JWT)
                        .build(),
                claims
        );

        signedJWT.sign(new MACSigner(secretKeyBytes));

        return signedJWT.serialize();
    }

    /**
     * Validate Token
     */
    public JWTClaimsSet validateAndGetClaims(
            String token
    ) throws Exception {

        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verified = signedJWT.verify(
                new MACVerifier(secretKeyBytes)
        );

        if (!verified) {
            throw new JOSEException("Invalid JWT Signature");
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        if (claims.getExpirationTime() == null) {
            throw new IllegalStateException(
                    "Token expiration missing"
            );
        }

        if (claims.getExpirationTime().before(new Date())) {
            throw new IllegalStateException(
                    "JWT Token Expired"
            );
        }

        return claims;
    }

    public Long getUserId(String token) throws Exception {
        return validateAndGetClaims(token)
                .getLongClaim("userId");
    }

    public String getEmail(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("email");
    }

    public String getName(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("name");
    }

    public boolean isTokenExpired(String token) {

        try {

            return validateAndGetClaims(token)
                    .getExpirationTime()
                    .before(new Date());

        } catch (Exception e) {
            return true;
        }
    }
}