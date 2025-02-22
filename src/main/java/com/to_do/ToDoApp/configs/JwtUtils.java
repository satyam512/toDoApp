package com.to_do.ToDoApp.configs;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.to_do.ToDoApp.constants.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET = "yourSecretKeyTwsometheinvjsecbekcvrefeewfeasefccseffdcfescsesefscecsececsefcscsecesecscefsfscesfsccecescescescesc"; // TODO From values

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}