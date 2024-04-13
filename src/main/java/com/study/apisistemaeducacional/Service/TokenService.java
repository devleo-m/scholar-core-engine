package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.LoginRequest;
import com.study.apisistemaeducacional.Controller.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final BCryptPasswordEncoder bCryptEncoder; // decifrar senhas
    private final JwtEncoder jwtEncoder; // codificar um JWT
    private final JwtDecoder jwtDencoder; // decofica um JWT

    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    public LoginResponse gerarToken(LoginRequest loginRequest){

        // Verifica se as credenciais são válidas (aqui você pode adicionar sua lógica de validação)
        if (!loginRequest.getNomeUsuario().equals("admin") || !loginRequest.getSenha().equals("admin")) {
            log.error("Credenciais inválidas");
            throw new RuntimeException("Credenciais inválidas");
        }

        Instant now = Instant.now();

        String scope = "admin"; // Defina o escopo com base nas informações do usuário (por exemplo, perfil)

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("projeto1")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO))
                .subject("1") // Aqui você pode definir o ID do usuário (ou outro identificador)
                .claim("scope", scope)
                .build();

        var valorJWT = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(valorJWT, TEMPO_EXPIRACAO);
    }

    public String buscaCampo(String token, String claim) {
        return jwtDencoder
                .decode(token)
                .getClaims()
                .get(claim)
                .toString();
    }
}
