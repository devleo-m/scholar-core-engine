package com.study.apisistemaeducacional.Security;

import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Iniciando o filtro de segurança");
        String token = this.recoverToken(request);
        if (token != null) {
            try {
                String login = tokenService.validateToken(token);
                log.info("Login validado: {}", login);

                if (login != null) {
                    UsuarioEntity user = userRepository.findByLogin(login)
                            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
                    List<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_" + user.getPapel().getNome().toUpperCase())
                    );
                    log.info("Role do usuário: {}", user.getPapel().getNome().toUpperCase());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Usuário autenticado: {}", user.getLogin());
                }
            } catch (Exception e) {
                log.error("Erro ao processar o token de autenticação", e);
            }
        }
        filterChain.doFilter(request, response);
        log.info("Filtro de segurança concluído");
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        log.info("Cabeçalho de Autorização: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}