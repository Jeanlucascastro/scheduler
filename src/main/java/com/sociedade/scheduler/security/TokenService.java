package com.sociedade.scheduler.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sociedade.scheduler.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String [] userClaims = null;

            if (user.getClaims() != null) {
                userClaims = user.getClaims().toArray(new String[0]);
            }

            String token = JWT.create()
                    .withIssuer("scheduler")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .withArrayClaim("user_claims", userClaims)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("scheduler")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public Boolean validateUserToken(String userName, String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("scheduler")
                    .build()
                    .verify(token)
                    .getSubject();

            return userName.equals(subject);
        } catch (JWTVerificationException exception){
            return false;
        }
    }
}
