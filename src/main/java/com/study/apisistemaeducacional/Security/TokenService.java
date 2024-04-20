package com.study.apisistemaeducacional.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UsuarioEntity user) {
        log.info("Gerando token para o usuário: {}", user.getLogin());
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("login-auth-api")
                .withSubject(user.getLogin())
                .withExpiresAt(Date.from(this.generateExpirationDate()))
                .sign(algorithm);
    }

    public String validateToken(String token) {
        if (token == null) {
            log.info("Token de validação é nulo");
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build();
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            log.error("Erro ao verificar token", exception);
            return null;
        }
    }

    private Instant generateExpirationDate(){
        log.info("Generando expiration date");
        return LocalDateTime.now().plusHours(90).toInstant(ZoneOffset.of("-03:00"));
    }
}