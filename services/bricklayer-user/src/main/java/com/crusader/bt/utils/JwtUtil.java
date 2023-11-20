package com.crusader.bt.utils;

import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.entity.UserRoleEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Класс для работы с JWT токенами авторизации
 */
@Component
public class JwtUtil {

    private static String secret;
    private static String expirationDurable;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }
    @Value("${jwt.expiration}")
    public void setExpirationDurable(String expirationDurable) {
        JwtUtil.expirationDurable = expirationDurable;
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

    /**
     * Генерация нового токена для пользователя,
     * время актуальности токена устанавливается в сутки
     */
    public static String generateToken(UserEntity user) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(
                "role",
                user.getRoles()
                        .stream()
                        .map(UserRoleEntity::getName)
                        .collect(Collectors.toList())
        );

        long expirationSeconds = Long.parseLong(expirationDurable);
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

}
