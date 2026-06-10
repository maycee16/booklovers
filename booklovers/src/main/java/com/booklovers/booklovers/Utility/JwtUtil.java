package com.booklovers.booklovers.Utility;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final Logger log =
            LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secretKeyString;

    private byte[] secretKeyBytes;

    // 24 HOURS
    private static final long EXPIRATION_MILLIS =
            24 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {

        if (secretKeyString == null || secretKeyString.isBlank()) {
            throw new IllegalStateException("JWT secret is missing");
        }

        secretKeyBytes = secretKeyString.getBytes();

        // HS256 requires minimum 32 bytes
        if (secretKeyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 32 bytes"
            );
        }

        log.info(" JWT Initialized Successfully");
    }

   

    public String generateToken(
            Long userId,
            String email,
            String role,
            String fullName
    ) throws JOSEException {

        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()

                // STANDARD CLAIMS
                .jwtID(UUID.randomUUID().toString())
                .subject(email)
                .issuer("PotterhouseClubsSystem")
                .issueTime(Date.from(now))
                .expirationTime(
                        Date.from(now.plusMillis(EXPIRATION_MILLIS))
                )

                // CUSTOM CLAIMS
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .claim("fullName", fullName)
                .claim("tokenType", "ACCESS_TOKEN")

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

        Date expiration = claims.getExpirationTime();

        if (expiration == null) {
            throw new IllegalStateException(
                    "Token expiration missing"
            );
        }

        if (expiration.before(new Date())) {
            throw new IllegalStateException(
                    "JWT Token Expired"
            );
        }

        return claims;
    }

    

    public String getEmail(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("email");
    }

    public String getUserId(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("userId");
    }

    public String getRole(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("role");
    }

    public String getFullName(String token) throws Exception {
        return validateAndGetClaims(token)
                .getStringClaim("fullName");
    }

    public boolean isTokenExpired(String token) {

        try {

            Date expiration = validateAndGetClaims(token)
                    .getExpirationTime();

            return expiration.before(new Date());

        } catch (Exception e) {

            return true;
        }
    }
}
    

