package com.crusader.bt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * Класс для работы с JWT токенами авторизации
 */
@Component
public class JwtUtil {

    private static String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    /**
     * Получение username из токена авторизации
     */
    public static String extractUsername(String token) {

        return getClaimsFromToken(token)
                .getSubject();
    }

    /**
     * Проверка токена на актуальность
     */
    public static boolean validateToken(String token) {

        return getClaimsFromToken(token)
                .getExpiration()
                .after(new Date());
    }

    /**
     * Получение ключевых полей из токена
     */
    public static Claims getClaimsFromToken(String token) {
        String key = Base64.getEncoder().encodeToString(secret.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
