package com.to_do.ToDoApp.configs;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.to_do.ToDoApp.constants.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final String SECRET = "yourSecretKey"; // TODO From values

    public Integer getUserIdFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
        return jwt.getClaim(AppConstants.USER_ID).asInt();
    }
}