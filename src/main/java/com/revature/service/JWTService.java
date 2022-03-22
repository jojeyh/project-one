package com.revature.service;

import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

public class JWTService {
    public static Logger logger = LoggerFactory.getLogger(JWTService.class);

    private static JWTService instance;
    private Key key;

    private JWTService() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    }

    public static JWTService getInstance() {
        if (JWTService.instance == null) {
            JWTService.instance = new JWTService();
        }

        return JWTService.instance;
    }

    public String createJWT(User user) {
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("user_role", user.getUserRole())
                .signWith(key)
                .compact();

        logger.info("JWT created: " + jwt);

        return jwt;
    }

    public Jws<Claims> parseJwt(String jwt) {
        try {
            Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

            return token;
        } catch (JwtException e) {
            e.printStackTrace(); // TODO get this into a log
            logger.warn("JWT was invalid");

            throw new UnauthorizedResponse("JWT was invalid");
        }
    }
}